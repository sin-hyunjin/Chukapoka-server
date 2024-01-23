package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.enums.ResultType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AuthNumberResponseDto {

    private ResultType result; // SUCCESS || ERROR
    private String email; // "xxxx@xxx.xxx"

    public AuthNumberResponseDto(ResultType result, String email) {
        this.result = result;
        this.email = email;
    }
}
