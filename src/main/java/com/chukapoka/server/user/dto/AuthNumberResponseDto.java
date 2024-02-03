package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.enums.ResultType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthNumberResponseDto {

    private ResultType result; // SUCCESS || ERROR
    private String email; // "xxxx@xxx.xxx"
    private String authNum; // 인증번호
    private LocalDateTime createdAt;  // 생성된 시간
    private LocalDateTime expireAt;   // 만료된 시간

}
