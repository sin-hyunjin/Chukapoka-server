package com.chukapoka.server.treemember.repository;

import com.chukapoka.server.treemember.domain.TreeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreeMemberRepository extends JpaRepository<TreeMember, Long> {
    /**
     * Email로 로그인을 하기 때문에 중복 가입 방지와 존재여부를 파악하는 메서드를 추가
     * @param email
     * @return
     */
    Optional<TreeMember> findByEmail(String email);
    boolean existsByEmail(String email);

}
