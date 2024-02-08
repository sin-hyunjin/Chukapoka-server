package com.chukapoka.server.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EmailCheckResponseDto {

    // 로그인 또는 회원가입으로 넘어감
    private String nextAction; // LOGIN || JOIN
    private String email;
}
