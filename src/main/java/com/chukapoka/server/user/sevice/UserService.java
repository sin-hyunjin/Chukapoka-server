package com.chukapoka.server.user.sevice;



import com.chukapoka.server.common.authority.JwtTokenProvider;
import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.dto.TokenRequestDto;
import com.chukapoka.server.common.entity.RefreshToken;
import com.chukapoka.server.common.enums.Authority;
import com.chukapoka.server.common.enums.EmailType;
import com.chukapoka.server.common.enums.NextActionType;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.common.exception.InvalidInputException;
import com.chukapoka.server.common.repository.RefreshTokenRepository;
import com.chukapoka.server.user.dto.*;
import com.chukapoka.server.user.entity.User;
import com.chukapoka.server.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    /** 이메일 체크 서비스
     * - 이메일이 등록되어 있는지 확인
     * - 등록된 이메일이면 로그인, 등록되지 않은 이메일이면 회원가입으로 처리
     */
    public EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequestDto) {
        String email = emailCheckRequestDto.getEmail();
        String emailType = emailCheckRequestDto.getEmailType();
        // 이메일이 이미 등록되어 있는지 확인
        if (userRepository.existsByEmailAndEmailType(email, emailType)) {
            // 이메일이 등록되어 있으면 {login, email} 값 반환
            return new EmailCheckResponseDto(NextActionType.LOGIN.getValue(), email);
        } else {
            // 등록되어 있지않다면 회원가입 {join, email} 값 반환
            return new EmailCheckResponseDto(NextActionType.JOIN.getValue(), email);
        }
    }

    /**
     * 사용자 인증 처리
     * - "type"이 "login"이면 사용자의 이메일과 비밀번호를 확인하여 로그인 처리
     * - "type"이 "join"이면 사용자를 등록
     */
    public UserResponseDto authenticateUser(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String password = userRequestDto.getPassword();
        String type = userRequestDto.getType(); // LOGIN || JOIN

        // 로그인
        if (NextActionType.LOGIN.getValue().equals(type)) {
            User user = authenticate(email, password);
            if (user != null) {
                // Authentication 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        new CustomUser(user.getId(), email, password, List.of(new SimpleGrantedAuthority(Authority.ROLE_USER.getAuthority()))),
                        null,
                        List.of(new SimpleGrantedAuthority(Authority.ROLE_USER.getAuthority()))
                );

                // JWT 토큰 생성
                TokenDto jwtToken = jwtTokenProvider.createToken(authentication);

                return new UserResponseDto(ResultType.SUCCESS, email, user.getId(), authentication, jwtToken);
            } else {
                return new UserResponseDto(ResultType.ERROR, email, null);
            }
        }
        // 회원가입
        if (NextActionType.JOIN.getValue().equals(type)) {
            Long newId = signIn(email, password);
            if(newId != null && newId != -1L){
                return new UserResponseDto(ResultType.SUCCESS, email, newId);
            }else {
                return new UserResponseDto(ResultType.ERROR, email, null);
            }
        }
        return new UserResponseDto(ResultType.ERROR, email, null);
    }

    // 데이터베이스에서 이메일과 비밀번호를 확인하는 로직
    private User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        // 저장된 비밀번호 해시와 입력된 비밀번호를 비교
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        // 사용자가 존재하지 않음
        return null;
    }
    // 회원가입 로직
    public Long signIn(String email, String password) {
        try {
            // BCryptPasswordEncoder를 사용하여 비밀번호를 해시화하여 저장
            String hashedPassword = passwordEncoder.encode(password);
            // 새로운 사용자 생성
            User newUser = User.builder()
                    .email(email)
                    .password(hashedPassword)
                    .emailType(EmailType.DEFAULT.name())
                    .build();

            User user= userRepository.save(newUser);

            return user.getId();
        } catch (Exception e) {
            
            e.printStackTrace();
            return -1L;
        }
    }

    /** 토큰 만료시 재발급
     *
     */
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
        // 2. Access Token 에서 user ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 user ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
        // 5. 새로운 토큰 생성
        TokenDto tokenDto =  jwtTokenProvider.createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
