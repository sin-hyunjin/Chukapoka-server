package com.chukapoka.server.treemember.dto;


import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import lombok.Data;

@Data
public class TreeUserRequestDto {

    private String email;
    private String password;
    private String type;

    public TreeUserRequestDto(String email, String password, String type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
