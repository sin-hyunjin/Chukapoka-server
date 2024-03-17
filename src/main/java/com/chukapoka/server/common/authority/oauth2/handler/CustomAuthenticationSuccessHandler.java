package com.chukapoka.server.common.authority.oauth2.handler;

import com.chukapoka.server.common.authority.jwt.JwtTokenProvider;
import com.chukapoka.server.common.dto.CustomUserDetails;
import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.dto.TokenResponseDto;
import com.chukapoka.server.common.entity.Token;
import com.chukapoka.server.common.repository.TokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/** OAuth2 인증이 성공했을 경우 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        TokenResponseDto token = saveToken(authentication, userDetails.getUser().getId().toString());

        super.onAuthenticationSuccess(request, response, authentication);
    }

    /** 토큰 생성 */
    private TokenResponseDto saveToken(Authentication authentication, String id){
        System.out.println("OAuth2 토큰 생성중");
        // JWT 토큰 생성
        TokenDto jwtToken = jwtTokenProvider.createToken(authentication);
        Token token = Token.builder()
                .key(id)
                .atValue(jwtToken.getAccessToken())
                .rtValue(jwtToken.getRefreshToken())
                .atExpiration(jwtToken.getAtExpiration())
                .rtExpiration(jwtToken.getRtExpiration())
                .build();
        return tokenRepository.save(token).toResponseDto();
}}

