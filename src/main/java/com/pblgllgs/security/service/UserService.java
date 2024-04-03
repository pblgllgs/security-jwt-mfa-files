package com.pblgllgs.security.service;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

import com.pblgllgs.security.entity.RoleEntity;

public interface UserService {

    void createUser(String firstName, String lastName, String email, String password);

    RoleEntity getRoleByName(String name);
}
