package com.pblgllgs.security.security;

import com.pblgllgs.security.constant.Constants;
import com.pblgllgs.security.domain.ApiAuthentication;
import com.pblgllgs.security.domain.UserPrincipal;
import com.pblgllgs.security.exception.ApiException;
import com.pblgllgs.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 *
 * @author pblgl
 * Created on 04-04-2024
 *
 */
@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var apiAuthentication = authenticationFunction.apply(authentication);
        var user = userService.getUserByEmail(apiAuthentication.getEmail());
        if (user != null){
            var userCredential = userService.getUserCredentialById(user.getId());
            if (userCredential.getUpdatedAt().minusDays(Constants.NINETY_DAYS).isAfter(LocalDateTime.now())){
                throw new ApiException("Credentials are expired. Please reset your password");
            }
            var userPrincipal = new UserPrincipal(user, userCredential);
            validAccount.accept(userPrincipal);
            if (bCryptPasswordEncoder.matches(apiAuthentication.getPassword(),userCredential.getPassword())){
                return ApiAuthentication.authenticated(user,userPrincipal.getAuthorities());
            } else throw new BadCredentialsException("Credentials invalid");
        }else{
            throw new ApiException("Unable to authenticate");
        }
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    private final Function<Authentication, ApiAuthentication> authenticationFunction = ApiAuthentication.class::cast;
    private final Consumer<UserPrincipal> validAccount = userPrincipal -> {
        if (userPrincipal.isAccountNonLocked()) {throw new LockedException("Your account is currently blocked");}
        if (userPrincipal.isEnabled()) {throw new DisabledException("Your account is currently disabled");}
        if (userPrincipal.isCredentialsNonExpired()) {throw new CredentialsExpiredException("Your credentials has expired. Please update your password");}
        if (userPrincipal.isAccountNonExpired()) {throw new DisabledException("Your password has expired");}
    };
}
