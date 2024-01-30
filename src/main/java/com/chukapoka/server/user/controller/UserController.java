package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.dto.TokenRequestDto;
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
        UserResponseDto responseDto = userService.authenticateUser(userRequestDTO);
        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    // 인증번호 요청 API
    @GetMapping("/authNumber")
    public BaseResponse<AuthNumberResponseDto> authNumber(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
            AuthNumberResponseDto responseDto = authNumberService.sendEmail(email);
            return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    // 토큰 만료시 재발급
    @PostMapping("/reissue")
    public BaseResponse<TokenDto> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = userService.reissueToken(tokenRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, tokenDto);
    }

    // 사용자 로그아웃
    @PostMapping("/logout")
        public BaseResponse<ResultType> logout(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        ResultType logout = userService.logout(tokenRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, logout);
    }
//    public BaseResponse<UserResponseDto> logout(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
//        UserResponseDto logout = userService.logout(tokenRequestDto);
//        return new BaseResponse<>(ResultType.SUCCESS, logout, "로그아웃 성공");
//    }


    @GetMapping("/test")
    public BaseResponse<TokenDto> checkToken(@RequestHeader("Authorization") String authorizationHeader) {
        TokenRequestDto tokenRequestDto = new TokenRequestDto();
        tokenRequestDto.setRefreshToken(authorizationHeader);

        TokenDto tokenDto = userService.reissueToken(tokenRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, tokenDto);
    }
}

