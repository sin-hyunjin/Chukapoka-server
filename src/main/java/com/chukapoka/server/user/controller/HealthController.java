package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.common.dto.TokenResponseDto;
import com.chukapoka.server.common.enums.NextActionType;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.user.dto.*;
import com.chukapoka.server.user.sevice.AuthNumberService;
import com.chukapoka.server.user.sevice.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class HealthController {


    /** 인증번호 요청 API */
    @GetMapping("/health")
    public BaseResponse<String> authNumber() {
            return new BaseResponse<>(ResultType.SUCCESS, "health");
    }



}

