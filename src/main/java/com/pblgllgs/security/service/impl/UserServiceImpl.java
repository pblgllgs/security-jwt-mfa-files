package com.pblgllgs.security.service.impl;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.entity.ConfirmationEntity;
import com.pblgllgs.security.entity.CredentialEntity;
import com.pblgllgs.security.entity.RoleEntity;
import com.pblgllgs.security.entity.UserEntity;
import com.pblgllgs.security.enums.Authority;
import com.pblgllgs.security.enums.EventType;
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
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        UserEntity userEntitySaved = userRepository.save(createNewUser(firstName, lastName, email));
        var credentialentity = new CredentialEntity(password,userEntitySaved);
        credentialRepository.save(credentialentity);
        var confirmationEntity = new ConfirmationEntity(userEntitySaved);
        publisher.publishEvent(new UserEvent(userEntitySaved, EventType.REGISTRATION, Map.of("key",confirmationEntity.getKey())));

    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByNameIgnoreCase(name).orElseThrow( () -> new ApiException("ROLE_NOT_FOUND"));
    }

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleByName(Authority.USER.name());
        return UserUtils.createUserEntity(firstName,lastName,email,role);
    }

}