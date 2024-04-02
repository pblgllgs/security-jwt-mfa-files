package com.pblgllgs.security.domain;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */


public class RequestContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private RequestContext(){}

    public static void start(){
        USER_ID.remove();
    }

    public static void setUserId(Long userId){
        USER_ID.set(userId);
    }

    public static Long getUserId(){
        return USER_ID.get();
    }
}
