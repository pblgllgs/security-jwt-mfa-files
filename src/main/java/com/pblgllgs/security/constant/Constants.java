package com.pblgllgs.security.constant;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

public class Constants {
    public static final String AUTHORITIES = "authorities";
    public static final String ROLE = "role";
    public static final String EMPTY_VALUE = "empty";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String USER_AUTHORITIES = "document:crate,document:read,document:update,document:delete";
    public static final String ADMIN_AUTHORITIES = "user:create,user:read,user:update,document:crate,document:read,document:update,document:delete";
    public static final String SUPER_ADMIN_AUTHORITIES =  "user:create,user:read,user:update,user:delete,document:crate,document:read,document:update,document:delete";
    public static final String MANAGER_AUTHORITIES = "document:crate,document:read,document:update,document:delete";
    public static final String TYPE = "typ";
    public static final String JWT_TYPE = "JWT";
    public static final String PBLGLLGS = "PBLGLLGS";
    public static final String LOGIN_PATH = "/user/login";
    public static final int NINETY_DAYS = 90;
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String ROLE_NOT_FOUND = "ROLE_NOT_FOUND";
    public static final String CREDENTIALS_NOT_FOUND = "CREDENTIALS_NOT_FOUND";
    public static final String CONFIRMATION_NOT_FOUND = "CONFIRMATION_NOT_FOUND";
}
