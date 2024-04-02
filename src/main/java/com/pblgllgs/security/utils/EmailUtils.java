package com.pblgllgs.security.utils;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name +",\n\nYour new account has been created. Please click on the link below to verify your account.\n\n" +
                getVerificationUrl(host,token) + "\n\nTheSupport Team";
    }

    private static String getVerificationUrl(String host, String token) {
        return host + "/verify/account?token="+ token;
    }

    public static String getResetPasswordMessage(String name, String host, String token) {
        return "Hello " + name +",\n\nYour new account has been created. Please click on the link below to verify your account.\n\n" +
                getResetPasswordUrl(host,token) + "\n\nTheSupport Team";
    }

    private static String getResetPasswordUrl(String host, String token) {
        return host + "/verify/password?token="+ token;
    }
}
