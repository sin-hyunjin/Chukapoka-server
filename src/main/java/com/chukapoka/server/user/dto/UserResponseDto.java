package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.enums.ResultType;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Data
@NoArgsConstructor
public class UserResponseDto {



    private ResultType result; // SUCCESS || ERROR
    private String email; // "xxxx@xxx.xxx"
    private Long id; // unique_userid
    private TokenDto jwtToken; // JWT 토큰

    public UserResponseDto(ResultType result, String email, Long id) {
        this.result = result;
        this.email = email;
        this.id = id;
    }

    public UserResponseDto(ResultType result, String email, Long id, Authentication authentication, TokenDto jwtToken) {
        this.result = result;
        this.email = email;
        this.id = id;
        this.jwtToken = jwtToken;
    }



}
