package com.chukapoka.server.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogoutRequestDto {
    @NotBlank(message = "accessToken is null")
    private String accessToken;
}
