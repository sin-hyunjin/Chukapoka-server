package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.NextActionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "nextAction is null")
    @ValidEnum(enumClass = NextActionType.class, message = "Type must be LOGIN or JOIN")
    private String type; // LOGIN || JOIN

}
