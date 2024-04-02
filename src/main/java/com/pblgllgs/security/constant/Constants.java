package com.pblgllgs.security.constant;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

public class Constants {
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String USER_AUTHORITIES = "document:crate,document:read,document:update,document:delete";
    public static final String ADMIN_AUTHORITIES = "user:create,user:read,user:update,document:crate,document:read,document:update,document:delete";
    public static final String SUPER_ADMIN_AUTHORITIES =  "user:create,user:read,user:update,user:delete,document:crate,document:read,document:update,document:delete";
    public static final String MANAGER_AUTHORITIES = "document:crate,document:read,document:update,document:delete";
}
