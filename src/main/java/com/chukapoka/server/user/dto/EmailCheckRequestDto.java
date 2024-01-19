package com.chukapoka.server.user.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailCheckRequestDto {

    private String email;
    // default, google, kakao
    private String emailType;


    public EmailCheckRequestDto(String email, String emailType) {
        this.email = email;
        this.emailType = emailType;
    }
}
