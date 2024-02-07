package com.chukapoka.server.user.controller;


import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.dto.TokenRequestDto;
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
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthNumberService authNumberService;

    // 이메일 체크 페이지
    @PostMapping("/emailCheck")
    public BaseResponse<EmailCheckResponseDto> emailCheck(@Valid @RequestBody EmailCheckRequestDto emailCheckRequestDTO) {
        EmailCheckResponseDto responseDTO = userService.emailCheck(emailCheckRequestDTO);
        return new BaseResponse<>(ResultType.SUCCESS, responseDTO);
    }

    // 로그인 또는 회원가입 처리
    @PostMapping
    public BaseResponse<UserResponseDto> joinAndLogin(@Valid @RequestBody UserRequestDto userRequestDTO) {
        UserResponseDto responseDto = null;



        if (Objects.equals(userRequestDTO.getType(), NextActionType.JOIN.getValue())) {
            responseDto = userService.join(userRequestDTO);
            // 회원가입 성공 시
            if ( responseDto.getResult() == ResultType.SUCCESS) {
                responseDto = userService.login(userRequestDTO);
                return new BaseResponse<>(ResultType.SUCCESS, responseDto);
            }

        }
        else if (userRequestDTO.getType() == "LOGIN") {
            responseDto = userService.login(userRequestDTO);

            return new BaseResponse<>(ResultType.SUCCESS, responseDto);
        }

        return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    // 인증번호 요청 API
    @GetMapping("/authNumber")
    public BaseResponse<AuthNumberResponseDto> authNumber(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
            AuthNumberResponseDto responseDto = authNumberService.authNumber(email);
            return new BaseResponse<>(ResultType.SUCCESS, responseDto);
    }

    // 토큰 만료시 재발급
    @PostMapping("/reissue")
    public BaseResponse<TokenDto> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = userService.reissue(tokenRequestDto);
        return new BaseResponse<>(ResultType.SUCCESS, tokenDto);
    }

//   사용자 로그아웃
    @PostMapping("/logout")
        public BaseResponse<ResultType> logout() {
        // 인증된 사용자 Id
        long userId = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        // 사용자의 ID를 기반으로 로그아웃 수행
        ResultType logout = userService.logout(userId);
        return new BaseResponse<>(ResultType.SUCCESS, logout);
    }


}

