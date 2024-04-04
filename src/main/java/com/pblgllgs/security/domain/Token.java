package com.pblgllgs.security.domain;
/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Token {
    private String access;
    private String refresh;
}
