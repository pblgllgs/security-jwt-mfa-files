package com.pblgllgs.security.service.impl;

import com.pblgllgs.security.domain.Token;
import com.pblgllgs.security.domain.TokenData;
import com.pblgllgs.security.dto.User;
import com.pblgllgs.security.enums.TokenType;
import com.pblgllgs.security.repository.UserRepository;
import com.pblgllgs.security.security.JwtConfiguration;
import com.pblgllgs.security.service.JwtService;
import com.pblgllgs.security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl extends JwtConfiguration implements JwtService {
    private final UserService userService;
    @Override
    public String createToken(User user, Function<Token, String> tokenFunction) {
        return null;
    }

    @Override
    public Optional<String> extractToken(HttpServletRequest request, String tokenType) {
        return Optional.empty();
    }

    @Override
    public void addCookie(HttpServletResponse response, User user, TokenType tokenType) {

    }

    @Override
    public <T> T getTokenData(String token, Function<TokenData, T> tokenFunction) {
        return null;
    }
}
