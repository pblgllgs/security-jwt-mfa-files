package com.pblgllgs.security.exception;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */

public class ApiException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super("An error occurred");
    }
}
