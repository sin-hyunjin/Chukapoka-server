package com.chukapoka.server.treemember.dto;


import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import lombok.Data;

@Data
public class AuthNumberCheckResponseDto {

    // Seccess : 인증번호 일치
    // Fail : 인증번호 불일치
    private TreeUserEnumType.ResultType result;
    private String email;

    public AuthNumberCheckResponseDto(TreeUserEnumType.ResultType result, String email) {
        this.result = result;
        this.email = email;
    }
}
