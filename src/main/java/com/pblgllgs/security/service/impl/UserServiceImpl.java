package com.pblgllgs.security.service.impl;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.cache.CacheStore;
import com.pblgllgs.security.domain.RequestContext;
import com.pblgllgs.security.entity.ConfirmationEntity;
import com.pblgllgs.security.entity.CredentialEntity;
import com.pblgllgs.security.entity.RoleEntity;
import com.pblgllgs.security.entity.UserEntity;
import com.pblgllgs.security.enums.Authority;
import com.pblgllgs.security.enums.EventType;
import com.pblgllgs.security.enums.LoginType;
import com.pblgllgs.security.event.UserEvent;
import com.pblgllgs.security.exception.ApiException;
import com.pblgllgs.security.repository.ConfirmationRepository;
import com.pblgllgs.security.repository.CredentialRepository;
import com.pblgllgs.security.repository.RoleRepository;
import com.pblgllgs.security.repository.UserRepository;
import com.pblgllgs.security.service.UserService;
import com.pblgllgs.security.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    //    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CacheStore<String,Integer> userCache;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        UserEntity userEntitySaved = userRepository.save(createNewUser(firstName, lastName, email));
        var credentialentity = new CredentialEntity(password, userEntitySaved);
        credentialRepository.save(credentialentity);
        var confirmationEntity = new ConfirmationEntity(userEntitySaved);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(userEntitySaved, EventType.REGISTRATION, Map.of("key", confirmationEntity.getKey())));

    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByNameIgnoreCase(name).orElseThrow(() -> new ApiException("ROLE_NOT_FOUND"));
    }

    @Override
    public void verifyAccountKey(String key) {
        ConfirmationEntity confirmationEntity = getUserConfirmation(key);
        UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        UserEntity userEntity = getUserEntityByEmail(email);
        RequestContext.setUserId(userEntity.getId());
        switch (loginType){
            case LOGIN_ATTEMPT -> {
                if(userCache.get(userEntity.getEmail()) == null){
                    userEntity.setLoginAttempts(0);
                    userEntity.setAccountNotBlock(true);
                }
                userEntity.setLoginAttempts(userEntity.getLoginAttempts() + 1);
                userCache.put(userEntity.getEmail(),userEntity.getLoginAttempts());
                if (userCache.get(userEntity.getEmail())> 5){
                    userEntity.setAccountNotBlock(false);
                }
            }
            case  LOGIN_SUCCESS -> {
                userEntity.setAccountNotBlock(true);
                userEntity.setLoginAttempts(0);
                userEntity.setLastLogin(LocalDateTime.now());
                userCache.evict(userEntity.getEmail());
            }
        }
        userRepository.save(userEntity);
    }

    private UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ApiException("USER_NOT_FOUND"));
    }

    private ConfirmationEntity getUserConfirmation(String key) {
        return confirmationRepository.findByKey(key).orElseThrow(() -> new ApiException("CONFIRMATION_NOT_FOUND"));
    }

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleByName(Authority.USER.name());
        return UserUtils.createUserEntity(firstName, lastName, email, role);
    }

}
