package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;

import com.chukapoka.server.common.enums.ResultType;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class HealthController {


    /** 인증번호 요청 API */
    @GetMapping("/health")
    public BaseResponse<String> authNumber() {
            return new BaseResponse<>(ResultType.SUCCESS, "health");
    }



}

