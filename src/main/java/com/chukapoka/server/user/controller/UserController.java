package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;

import com.chukapoka.server.user.dto.*;
import com.chukapoka.server.user.sevice.AuthNumberService;
import com.chukapoka.server.user.sevice.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthNumberService authNumberService;

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

    // 인증번호 요청 API
    @GetMapping("/authNumber")
    public BaseResponse<AuthNumberResponseDto> authNumber(
            @RequestParam("email") String email) {
        try {
            // 인증번호를 요청하고 결과를 받음
            String authNumber = authNumberService.sendEmail(email);

            // 성공적으로 인증번호를 보냈을 경우
            return new BaseResponse<>(ResultType.SUCCESS, new AuthNumberResponseDto(ResultType.SUCCESS, email));
        } catch (MessagingException | UnsupportedEncodingException e) {
            // 실패 시 에러 응답
            return new BaseResponse<>(ResultType.ERROR, null);
        }
    }
}

