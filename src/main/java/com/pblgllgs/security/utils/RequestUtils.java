package com.pblgllgs.security.utils;
/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.security.domain.Response;
import com.pblgllgs.security.exception.ApiException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.*;

public class RequestUtils {

    private static final BiConsumer<HttpServletResponse, Response> writeResponse = (httpServletResponse, response) -> {
        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            new ObjectMapper().writeValue(outputStream, response);
            outputStream.flush();
        } catch (Exception ex) {
            throw new ApiException(ex.getMessage());
        }
    };

    private static final BiFunction<Exception, HttpStatus, String> errorReason = (ex, status) -> {
        if (status.isSameCodeAs(FORBIDDEN)) {
            return "You do not have enough permissions";
        }
        if (status.isSameCodeAs(UNAUTHORIZED)) {
            return "You are not login";
        }
        if (ex instanceof DisabledException
                || ex instanceof LockedException
                || ex instanceof BadCredentialsException
                || ex instanceof CredentialsExpiredException
                || ex instanceof ApiException
        ) {
            return ex.getMessage();
        }
        if (status.is5xxServerError()) {
            return "Internal Server Error occurred";
        } else {
            return "An error occurred, please try again";
        }
    };

    public static Response getResponse(
            HttpServletRequest request,
            Map<?, ?> data,
            String message,
            HttpStatus status
    ) {
        return new Response(
                LocalDateTime.now().toString(),
                status.value(),
                request.getRequestURI(),
                valueOf(status.value()),
                message,
                EMPTY,
                data
        );
    }

    public static void handleErrorResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception ex) {
        if (ex instanceof AccessDeniedException) {
            Response apiResponse = getErrorResponse(request, response, ex, FORBIDDEN);
            writeResponse.accept(response, apiResponse);
        }
    }

    private static Response getErrorResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception ex,
            HttpStatus status
    ) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        return new Response(
                LocalDateTime.now().toString(),
                status.value(),
                request.getRequestURI(),
                valueOf(status.value()),
                errorReason.apply(ex, status),
                getRootCauseMessage(ex), Collections.emptyMap()
        );
    }
}
