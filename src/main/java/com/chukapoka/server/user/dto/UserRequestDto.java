package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.EmailType;
import com.chukapoka.server.common.enums.NextActionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank(message = "email type is null")
    @ValidEnum(enumClass = EmailType.class, message = "email type is DEFAULT, GOOGLE, KAKAO")
    private String emailType;

    @NotBlank(message = "password is null")
    private String password;

    @NotBlank(message = "actionType is null")
    @ValidEnum(enumClass = NextActionType.class, message = "actionType must be LOGIN or JOIN")
    private String actionType;

}
