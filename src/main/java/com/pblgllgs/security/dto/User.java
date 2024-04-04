package com.pblgllgs.security.dto;
/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private Long createdBy;
    private Long updatedBy;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String bio;
    private String imageUrl;
    private String qrCodeSecret;
    private String lastLogin;
    private String createdAt;
    private String updatedAt;
    private String role;
    private String authorities;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNotBlock;
    private boolean enabled;
    private boolean mfa;
}
