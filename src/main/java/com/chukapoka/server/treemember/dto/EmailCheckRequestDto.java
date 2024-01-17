package com.chukapoka.server.treemember.dto;


import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import lombok.Data;

@Data
public class EmailCheckRequestDto {

    private String email;
    // default, google, kakao
    private TreeUserEnumType.EmailType emailType;


    public EmailCheckRequestDto(String email, TreeUserEnumType.EmailType emailType) {
        this.email = email;
        this.emailType = emailType;
    }
}
