package com.chukapoka.server.treemember.repository;


import com.chukapoka.server.treemember.domain.TreeUser;
import com.chukapoka.server.treemember.domain.TreeUserEnumType;
import com.chukapoka.server.treemember.dto.TreeUserRequestDto;
import com.chukapoka.server.treemember.dto.TreeUserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreeUserRepository extends JpaRepository<TreeUser, Long> {
    /**
     * Email로 로그인을 하기 때문에 중복 가입 방지와 존재여부를 파악하는 메서드를 추가
     */

    TreeUser findByEmail(String email);

    // 이메일이 등록되어있는지 이메일과 이메일타입 확인
    boolean existsByEmailAndEmailType(String email, TreeUserEnumType.EmailType emailType);


    // 데이터베이스에 이메일 비밀번호 확인 (토큰으로 해야함)
    TreeUserRequestDto authenticate(String email, String password);

    // 회원가입 로직 구현해야함 (사용자 입력값에 뭐가 들어갈지?)
    TreeUserRequestDto registerUser(String email, String password);
}
