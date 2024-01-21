package com.chukapoka.server.user.dto;

import com.chukapoka.server.common.enums.ResultType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private ResultType result; // SUCCESS || FAIL
    private String email; // "xxxx@xxx.xxx"
    private String id; // unique_userid

    public UserResponseDto(ResultType result, String email, String id) {
        this.result = result;
        this.email = email;
        this.id = id;
    }
}
