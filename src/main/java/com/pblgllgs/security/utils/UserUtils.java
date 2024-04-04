package com.pblgllgs.security.utils;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.constant.Constants;
import com.pblgllgs.security.dto.User;
import com.pblgllgs.security.entity.CredentialEntity;
import com.pblgllgs.security.entity.RoleEntity;
import com.pblgllgs.security.entity.UserEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class UserUtils {

    public static UserEntity createUserEntity(String firstName, String lastName, String email, RoleEntity roleEntity){
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLogin(LocalDateTime.now())
                .accountNonExpired(true)
                .accountNotBlock(true)
                .mfa(false)
                .enabled(false)
                .loginAttempts(0)
                .qrCodeSecret(EMPTY)
                .phone(EMPTY)
                .bio(EMPTY)
                .imageUrl("https://www.truckeradvisor.com/media/uploads/profilePics/notFound.jpg")
                .role(roleEntity)
                .build();
    }

    public static User fromUserEntity(UserEntity userEntity, RoleEntity role, CredentialEntity credentialEntity) {
        User user =  new User();
        BeanUtils.copyProperties(userEntity,user);
        user.setLastLogin(userEntity.getLastLogin().toString());
        user.setCredentialsNonExpired(isCredentialNonExpired(credentialEntity));
        user.setCreatedAt(userEntity.getCreatedAt().toString());
        user.setUserId(userEntity.getUpdatedAt().toString());
        user.setRole(role.getName());
        user.setAuthorities(role.getAuthorities().getValue());
        return user;
    }

    public static boolean isCredentialNonExpired(CredentialEntity credentialEntity) {
        return credentialEntity.getUpdatedAt().plusDays(Constants.NINETY_DAYS).isAfter(LocalDateTime.now());
    }
}
