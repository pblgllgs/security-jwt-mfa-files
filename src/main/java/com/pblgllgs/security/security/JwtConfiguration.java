package com.pblgllgs.security.security;
/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfiguration {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;
}
