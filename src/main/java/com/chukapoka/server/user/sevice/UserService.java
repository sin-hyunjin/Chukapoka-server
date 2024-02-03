package com.chukapoka.server.user.sevice;



import com.chukapoka.server.common.authority.JwtTokenProvider;

import com.chukapoka.server.common.dto.CustomUser;
import com.chukapoka.server.common.dto.TokenDto;
import com.chukapoka.server.common.dto.TokenRequestDto;
import com.chukapoka.server.common.entity.Token;
import com.chukapoka.server.common.enums.Authority;
import com.chukapoka.server.common.enums.EmailType;
import com.chukapoka.server.common.enums.NextActionType;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.common.repository.TokenRepository;
import com.chukapoka.server.user.dto.*;
import com.chukapoka.server.user.entity.User;
import com.chukapoka.server.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

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
                        new CustomUser(user.getId(), password, List.of(new SimpleGrantedAuthority("ROLE" + Authority.USER.getAuthority()))),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + Authority.USER.getAuthority()))
                );


                // JWT 토큰 생성
                TokenDto jwtToken = jwtTokenProvider.createToken(authentication);

                Token token = Token.builder()
                        .key(authentication.getName())
                        .atValue(jwtToken.getAccessToken())
                        .rtValue(jwtToken.getRefreshToken())
                        .build();

                tokenRepository.save(token);
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
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // Optional에서 User 객체를 얻기
        User user = optionalUser.orElse(null);

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
            // 권한을 ROLE_USER로 설정
            String authorities = "ROLE_" + Authority.USER.getAuthority();
            System.out.println("authorities = " + authorities);
            // 새로운 사용자 생성
            User newUser = User.builder()
                    .email(email)
                    .password(hashedPassword)
                    .emailType(EmailType.DEFAULT.name())
                    .authorities(authorities)
                    .build();

            User user= userRepository.save(newUser);

            return user.getId();
        } catch (Exception e) {

            e.printStackTrace();
            return -1L;
        }
    }


    /**
     * 로그아웃 처리
     * - 클라이언트에서 전달한 Access Token과 Refresh Token을 사용하여 로그아웃 처리
     */
    // UserService.java
    public ResultType logout(LogoutRequestDto logoutRequestDto) {
        String accessToken = logoutRequestDto.getAccessToken();

        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return ResultType.ERROR;
        }

        // 2. Access Token에서 user ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // 3. 저장소에서 user ID를 기반으로 Refresh Token 값 가져오기
        Token refreshTokenEntity = tokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자입니다."));

        // 4. 저장소에서 Refresh Token 제거
        tokenRepository.delete(refreshTokenEntity);
        return ResultType.SUCCESS;
    }

//    public ResultType logout(TokenRequestDto tokenRequestDto) {
//
//        String accessToken = tokenRequestDto.getAccessToken();
//        String refreshToken = tokenRequestDto.getRefreshToken();
//
//        // 1. Access Token 검증
//        if (!jwtTokenProvider.validateToken(accessToken)) {
//            return ResultType.ERROR;
//        }
//
//        // 2. Access Token에서 user ID 가져오기
//        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//
//        // 3. 저장소에서 user ID를 기반으로 Refresh Token 값 가져오기
//        Token refreshTokenEntity = tokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자입니다."));
//
//        // 4. Refresh Token이 클라이언트에서 전달한 값과 일치하는지 확인
//        if (!refreshTokenEntity.getRtValue().equals(refreshToken)) {
//            return ResultType.ERROR;
//        }
//
//        // 5. 저장소에서 Refresh Token 제거
//        tokenRepository.delete(refreshTokenEntity);
//
//        // 6. 클라이언트에서 Access Token 제거
//        return ResultType.SUCCESS;
//
//    }


    /** 토큰 만료시 재발급
     * Access Token을 복호화하여 유저 정보 (USER ID)를 가져오고 저장소에 있는 Refresh Token과 클라이언트가 전달한 Refresh Token의 일치 여부를 검사한다.
     * 만약 일치한다면 로그인했을 때와 동일하게 새로운 토큰을 생성해서 클라이언트에게 전달한다.
     * Refresh Token 은 재사용하지 못하게 저장소에서 값을 갱신해준다.
     */
    public TokenDto reissueToken(TokenRequestDto tokenRequestDto) {

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 user ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 user ID 를 기반으로 Refresh Token 값 가져옴
        Token refreshToken = tokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getRtValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. Access Token이 만료되었다면 새로운 토큰 생성 및 저장소 정보 업데이트
        if (jwtTokenProvider.isTokenExpired(tokenRequestDto.getAccessToken())) {
            TokenDto newTokenDto = jwtTokenProvider.createToken(authentication);
            Token newRefreshToken = Token.builder()
                    .key(authentication.getName())
                    .rtValue(newTokenDto.getRefreshToken())
                    .build();

            tokenRepository.save(newRefreshToken);

            return newTokenDto;
        }

        // 6. Access Token이 유효하면 기존 토큰 반환
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(tokenRequestDto.getAccessToken())
                .refreshToken(tokenRequestDto.getRefreshToken())
                .accessTokenExpiresIn(jwtTokenProvider.getExpirationTime(tokenRequestDto.getAccessToken()))
                .build();
    }
}
