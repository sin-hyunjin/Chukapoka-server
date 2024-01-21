package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.enums.NextActionType;
import lombok.Data;


@Data
public class EmailCheckResponseDto {

    // 로그인 또는 회원가입으로 넘어감
    private String nextAction; // LOGIN || JOIN
    private String email;


    public EmailCheckResponseDto(String nextAction, String email) {
        this.nextAction = nextAction;
        this.email = email;
    }
}
