package com.pblgllgs.security.security;
/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.security.constant.Constants;
import com.pblgllgs.security.domain.ApiAuthentication;
import com.pblgllgs.security.domain.Response;
import com.pblgllgs.security.dto.User;
import com.pblgllgs.security.dto.request.LoginRequest;
import com.pblgllgs.security.enums.LoginType;
import com.pblgllgs.security.enums.TokenType;
import com.pblgllgs.security.service.JwtService;
import com.pblgllgs.security.service.UserService;
import com.pblgllgs.security.utils.RequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService) {
        super(new AntPathRequestMatcher(Constants.LOGIN_PATH, HttpMethod.POST.name()), authenticationManager);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            LoginRequest user = new ObjectMapper()
                    .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true)
                    .readValue(request.getInputStream(), LoginRequest.class);
            userService.updateLoginAttempt(user.getEmail(), LoginType.LOGIN_ATTEMPT);
            ApiAuthentication authentication = ApiAuthentication.unauthenticated(user.getEmail(), user.getPassword());
            return getAuthenticationManager().authenticate(authentication);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            RequestUtils.handleErrorResponse(request, response, ex);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException {
        var user = (User) authentication.getPrincipal();
        userService.updateLoginAttempt(user.getEmail(), LoginType.LOGIN_SUCCESS);
        var httpResponse = user.isMfa() ? sendQrCode(request, user) : sendResponse(request, response, user);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        var out = response.getOutputStream();
        var mapper = new ObjectMapper();
        mapper.writeValue(out, httpResponse);
        out.flush();
    }

    private Response sendResponse(HttpServletRequest request, HttpServletResponse response, User user) {
        jwtService.addCookie(response, user, TokenType.ACCESS);
        jwtService.addCookie(response, user, TokenType.REFRESH);
        return RequestUtils.getResponse(
                request,
                Map.of("user", user),
                "Login Success",
                HttpStatus.OK
        );
    }

    private Response sendQrCode(HttpServletRequest request, User user) {
        return RequestUtils.getResponse(
                request,
                Map.of("user", user),
                "Please enter QR code",
                HttpStatus.OK
        );
    }
}