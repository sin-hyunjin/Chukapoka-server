package com.chukapoka.server.common.dto;


import com.chukapoka.server.common.enums.ResultType;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private String resultCode;
    private T data;
    private String message;

    public BaseResponse(ResultType resultCode, T data) {
        this.resultCode = resultCode.name();
        this.data = data;
        this.message = resultCode.getMsg();
    }

    public BaseResponse(ResultType resultCode, T data, String message) {
        this.resultCode = resultCode.name();
        this.data = data;
        this.message = message;
    }
}
