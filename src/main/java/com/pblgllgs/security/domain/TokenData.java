package com.pblgllgs.security.domain;
/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */

import com.pblgllgs.security.dto.User;
import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class TokenData {
    private User user;
    private Claims claims;
    private boolean valid;
    private List<GrantedAuthority> authorities;
}
