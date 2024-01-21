package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;

import com.chukapoka.server.user.dto.EmailCheckRequestDto;
import com.chukapoka.server.user.dto.EmailCheckResponseDto;
import com.chukapoka.server.user.dto.UserRequestDto;
import com.chukapoka.server.user.dto.UserResponseDto;
import com.chukapoka.server.user.sevice.UserService;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 이메일 체크 페이지
    @PostMapping("/emailcheck")
    public BaseResponse<EmailCheckResponseDto> checkEmail(@Valid @RequestBody EmailCheckRequestDto emailCheckRequestDTO) {
        EmailCheckResponseDto responseDTO = userService.checkEmail(emailCheckRequestDTO);
        return new BaseResponse<>(ResultType.SUCCESS, responseDTO);
    }


    // 로그인 또는 회원가입 처리
    @PostMapping("")
    public BaseResponse<UserResponseDto> authenticateUser(@Valid @RequestBody UserRequestDto userRequestDTO) {
        UserResponseDto responseDTO = userService.authenticateUser(userRequestDTO);
        return new BaseResponse<>(ResultType.SUCCESS, responseDTO);
    }


}

