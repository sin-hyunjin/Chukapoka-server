package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.enums.ResultType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private ResultType result; // SUCCESS || ERROR
    private String email; // "xxxx@xxx.xxx"
    private Long id; // unique_userid

    public UserResponseDto(ResultType result, String email, Long id) {
        this.result = result;
        this.email = email;
        this.id = id;
    }
}
