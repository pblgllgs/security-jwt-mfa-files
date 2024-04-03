package com.pblgllgs.security.utils;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String key) {
        return "Hello " + name +",\n\nYour new account has been created. Please click on the link below to verify your account.\n\n" +
                getVerificationUrl(host,key) + "\n\nThe Support Team";
    }

    private static String getVerificationUrl(String host, String key) {
        return host + "/verify/account?key="+ key;
    }

    public static String getResetPasswordMessage(String name, String host, String key) {
        return "Hello " + name +",\n\nYour new account has been created. Please click on the link below to verify your account.\n\n" +
                getResetPasswordUrl(host,key) + "\n\nTheSupport Team";
    }

    private static String getResetPasswordUrl(String host, String key) {
        return host + "/verify/password?key="+ key;
    }
}
