package com.chukapoka.server.user.controller;


import com.chukapoka.server.user.dto.*;

import com.chukapoka.server.user.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 이메일 체크 페이지
    @PostMapping("/emailcheck")
    public ResponseEntity<EmailCheckResponseDto> checkEmail(@RequestBody EmailCheckRequestDto emailCheckRequestDTO) {

        EmailCheckResponseDto responseDTO = userService.checkEmail(emailCheckRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

}

