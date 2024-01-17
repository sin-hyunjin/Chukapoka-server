package com.chukapoka.server.treemember.dto;


import com.chukapoka.server.treemember.domain.TreeUserEnumType;

/**
 * 인증번호 서비스
 */
public class AuthNumberResponseDto {
    // 등록되지 않은 이메일일 경우 인증번호 전송 요청
    // Result success는 인증번호 전송 성공, fail은 인증번호 전송 실패(잘못된 이메일 형식)
    private TreeUserEnumType.ResultType result;
    private String email;

    public AuthNumberResponseDto(TreeUserEnumType.ResultType result, String email) {
        this.result = result;
        this.email = email;
    }
}
