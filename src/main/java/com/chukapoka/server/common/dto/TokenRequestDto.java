package com.chukapoka.server.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenRequestDto {

    @NotBlank(message = "accessToken is null")
    private String accessToken;
    @NotBlank(message = "refreshToken is null")
    private String refreshToken;
}
