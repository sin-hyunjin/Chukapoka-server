package com.chukapoka.server.user.dto;


import com.chukapoka.server.common.annotation.ValidEnum;
import com.chukapoka.server.common.enums.EmailType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCheckRequestDto {


    @Email
    @NotBlank(message = "email is null")
    private String email;
    // default, google, kakao

    @NotBlank(message = "email type is null")
    @ValidEnum(enumClass = EmailType.class, message = "email type is DEFAULT, GOOGLE, KAKAO")
    private String emailType;

}
