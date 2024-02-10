package com.chukapoka.server.user.controller;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.user.dto.EmailCheckRequestDto;
import com.chukapoka.server.user.dto.EmailCheckResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actuator")
public class HealthController {

    @GetMapping("/health")
    public BaseResponse<String> health() {
        return new BaseResponse<>(ResultType.SUCCESS, "health");
    }

}
