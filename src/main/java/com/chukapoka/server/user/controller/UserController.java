package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.dto.TokenRequestDto;
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
//    @PostMapping("/logout")
//        public BaseResponse<ResultType> logout(@Valid @RequestBody LogoutRequestDto logoutRequestDto) {
//        ResultType logout = userService.logout(logoutRequestDto);
//        return new BaseResponse<>(ResultType.SUCCESS, logout);
//    }
    @PostMapping("/logout")
    public BaseResponse<ResultType> logout(@Valid @RequestHeader("Authorization") String authorizationHeader) {

        // "Bearer " 다음의 토큰 값만 추출
        String accessToken = authorizationHeader.substring(7);
        LogoutRequestDto logoutRequestDto = new LogoutRequestDto();
        System.out.println("accessToken = " + accessToken);
        logoutRequestDto.setAccessToken(accessToken);

        ResultType logout = userService.logout(logoutRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, logout);
    }



    @GetMapping("/test")
    public BaseResponse<UserResponseDto> searchMyInfo() {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUser.getUserId();

        // userId 등을 이용하여 사용자 정보를 가져오는 로직
        // ...

        // 가져온 사용자 정보로 MemberDtoResponse를 생성
        UserResponseDto response = new UserResponseDto();
        response.setId(userId);
        // 다른 필요한 정보들을 설정

        return new BaseResponse<>(ResultType.SUCCESS, response);
    }

}

