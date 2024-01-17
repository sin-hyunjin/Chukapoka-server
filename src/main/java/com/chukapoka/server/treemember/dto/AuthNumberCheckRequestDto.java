package com.chukapoka.server.treemember.dto;

import lombok.Data;

@Data
public class AuthNumberCheckRequestDto {
    //입력된 인증번호 일치여부 확인
    private String email;
    private String authNumber;

}
