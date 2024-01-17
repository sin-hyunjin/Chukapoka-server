package com.chukapoka.server.treemember.dto;

import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import lombok.Data;

@Data
public class TreeUserResponseDto {


    private TreeUserEnumType.ResultType result;
    private String email;
    // unique_userid_1234
    // 다른 사람에게 이메일이 노출되지 않게, 식별자용 id가 필요함
    private String id;

    public TreeUserResponseDto(TreeUserEnumType.ResultType result, String email, String id) {
        this.result = result;
        this.email = email;
        this.id = id;
    }
}
