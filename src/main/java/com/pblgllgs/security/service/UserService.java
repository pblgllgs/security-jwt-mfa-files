package com.pblgllgs.security.service;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.entity.RoleEntity;
import com.pblgllgs.security.enums.LoginType;

public interface UserService {

    void createUser(String firstName, String lastName, String email, String password);

    RoleEntity getRoleByName(String name);

    void verifyAccountKey(String key);
    void updateLoginAttempt(String email, LoginType loginType);
}
