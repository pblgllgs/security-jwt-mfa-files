package com.pblgllgs.security.resource;

import com.pblgllgs.security.domain.Response;
import com.pblgllgs.security.dtorequest.UserRequest;
import com.pblgllgs.security.service.UserService;
import com.pblgllgs.security.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collections;

/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */
@RestController
@RequestMapping(path = {"/user"})
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest userRequest, HttpServletRequest request) {
        userService.createUser(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );
        return ResponseEntity
                .created(getUri())
                .body(
                        RequestUtils.getResponse(
                                request,
                                Collections.emptyMap(),
                                "Account Created. Check your email to enable your account",
                                HttpStatus.CREATED
                        )
                );
    }

    private URI getUri() {
        return URI.create("");
    }
}
