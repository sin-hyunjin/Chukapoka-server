package com.chukapoka.server.treemember.controller;


import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import com.chukapoka.server.treemember.dto.*;

import com.chukapoka.server.treemember.sevice.TreeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class TreeUserController {
    @Autowired
    private TreeUserService treeUserService;

    // 이메일 체크 페이지
    @PostMapping("/emailcheck")
    public ResponseEntity<EmailCheckResponseDto> checkEmail(@RequestBody EmailCheckRequestDto emailCheckRequestDTO) {
        EmailCheckResponseDto responseDTO = treeUserService.checkEmail(emailCheckRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 이메일 체크후 타입이 join, login 확인후 처리
    @PostMapping("")
    public ResponseEntity<TreeUserResponseDto> authenticateUser(@RequestBody TreeUserRequestDto treeUserRequestDto) {
        TreeUserResponseDto responseDTO = treeUserService.authenticateUser(treeUserRequestDto);
        return ResponseEntity.ok(responseDTO);
    }

    // 등록되지 않은 이메일일 경우 인증번호 요청
    @GetMapping("/authNumber?email={email}")
    public AuthNumberResponseDto requestAuthNumber(@RequestParam String email) {

        // 데이터는 {Success, email} 타입으로 반환
        return new AuthNumberResponseDto(TreeUserEnumType.ResultType.Success,email);
    }

    // 이메일 인증번호 확인
    @PostMapping("/authNumber")
    public ResponseEntity<AuthNumberCheckResponseDto> checkAuthNumber(@RequestBody AuthNumberCheckRequestDto authNumberCheckRequestDto) {
        AuthNumberCheckResponseDto responseDTO = treeUserService.checkAuthNumber(authNumberCheckRequestDto);
        return ResponseEntity.ok(responseDTO);
    }
}

