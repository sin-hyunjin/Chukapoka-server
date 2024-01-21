package com.chukapoka.server.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Email
    @NotBlank(message = "email is null")
    private String email;

    @NotBlank(message = "password is null")
    private String password;

    @Pattern(regexp = "join|login", message = "Invalid type. Allowed values: join, login")
    private String type; // join || login

}
