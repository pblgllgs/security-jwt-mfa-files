package com.pblgllgs.security.dto.request;
/*
 *
 * @author pblgl
 * Created on 03-04-2024
 *
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
    @NotBlank(message = "Email can not be empty or null")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "First name can not be empty or null")
    private String password;

}
