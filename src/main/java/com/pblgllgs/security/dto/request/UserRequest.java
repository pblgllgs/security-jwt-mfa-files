package com.pblgllgs.security.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 *
 * @author pblgl
 * Created on 02-04-2024
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    @NotBlank(message = "First name cannot be empty or null")
    private String firstName;
    @NotBlank(message = "Last name can not be empty or null")
    private String lastName;
    @NotBlank(message = "Email can not be empty or null")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "First name can not be empty or null")
    private String password;
    private String bio;
    private String phone;
}
