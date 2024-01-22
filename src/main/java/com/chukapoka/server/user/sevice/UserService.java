package com.chukapoka.server.user.sevice;



import com.chukapoka.server.common.enums.EmailType;
import com.chukapoka.server.common.enums.NextActionType;
import com.chukapoka.server.common.enums.ResultType;
import com.chukapoka.server.user.dto.*;
import com.chukapoka.server.user.entity.User;
import com.chukapoka.server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                return new UserResponseDto(ResultType.SUCCESS, email, user.getId());
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
            System.out.println("user = " + user);
            return user.getId();
        } catch (Exception e) {
            
            e.printStackTrace();
            return -1L;
        }
    }
}
