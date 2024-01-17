package com.chukapoka.server.treemember.sevice;



import com.chukapoka.server.treemember.domain.TreeUser;
import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import com.chukapoka.server.treemember.dto.*;
import com.chukapoka.server.treemember.repository.TreeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TreeUserService {
    private final TreeUserRepository treeUserRepository;

    @Autowired
    public TreeUserService(TreeUserRepository treeUserRepository) {
        this.treeUserRepository = treeUserRepository;
    }


    /** 이메일 체크 서비스
     * - 이메일이 등록되어 있는지 확인
     * - 등록된 이메일이면 로그인, 등록되지 않은 이메일이면 회원가입으로 처리
     */
    public EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequestDto) {
        String email = emailCheckRequestDto.getEmail();
        TreeUserEnumType.EmailType emailType = emailCheckRequestDto.getEmailType();

        // 이메일이 이미 등록되어 있는지 확인
        if (treeUserRepository.existsByEmailAndEmailType(email, emailType)) {
            // 이메일이 등록되어 있으면 {login, email} 값 반환
            return new EmailCheckResponseDto("login", email);
        } else {
            // 등록되어 있지않다면 회원가입 {join, email} 값 반환
            return new EmailCheckResponseDto("join", email);
        }
    }

    /**
     * 사용자 인증 처리
     * - "type"이 "login"이면 사용자의 이메일과 비밀번호를 확인하여 로그인 처리
     * - "type"이 "join"이면 사용자를 등록
     */
    public TreeUserResponseDto authenticateUser(TreeUserRequestDto userRequestDTO) {
        String email = userRequestDTO.getEmail();

        String password = userRequestDTO.getPassword();
        String type = userRequestDTO.getType(); // login || join

        if ("login".equals(type)){
            // 등록된 이메일과 비밀번호 일치 여부

            //unique_userid_1234는 식별자용 아이디임 토큰처리 해야함
            return new TreeUserResponseDto(TreeUserEnumType.ResultType.Success, email, "unique_userid_1234");

        }else {
            // 회원가입 로그인 처리
            if ("join".equals(type)) {
                // 사용자 등록
                TreeUser newUser = TreeUser.builder()
                        .email(email)
                        .password(password)  // 실제로는 데이터베이스에 저장하기 전에 비밀번호를 해싱 처리해야함
                        .emailType(TreeUserEnumType.EmailType.Default) // 이메일 선택안하면 기본적으로 Default값으로 설정
                        .build();

                treeUserRepository.save(newUser);

                return new TreeUserResponseDto(TreeUserEnumType.ResultType.Success, email, "unique_userid_1234");

            }
        }
        return new TreeUserResponseDto(TreeUserEnumType.ResultType.Fail, email, null);
    }

    /** 등록되지 않는 이메일 인증번호 일치여부 확인 로직
     *
     */
    public AuthNumberCheckResponseDto checkAuthNumber(AuthNumberCheckRequestDto authNumberCheckRequestDto) {
        String email = authNumberCheckRequestDto.getEmail();
        String authNumber = authNumberCheckRequestDto.getAuthNumber();

        // 가상의 인증번호 ( 실제로는 DB, 캐쉬등에서 조회해야함)
        String storedAuthNumber = "123456";

        // 입력된 인증번호와 저장된 인증번호를 비교하여 결과를 반환
        if (storedAuthNumber.equals(authNumber)) {
            // 인증번호 일치
            return new AuthNumberCheckResponseDto(TreeUserEnumType.ResultType.Success, email);
        } else {
            // 인증번호 불일치
            return new AuthNumberCheckResponseDto(TreeUserEnumType.ResultType.Fail, email);
        }
    }
}
