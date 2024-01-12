package com.chukapoka.server.treemember.domain;


import com.chukapoka.server.common.status.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_user")
public class TreeMember {

    /**
     * Security Test를 위한 사용자 도메인
     * 이메일, 비밀번호, 권한만을 가지고 있으며, 이메일로 로그인하도록 만들예정
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = true, unique = true)
    private String email;
//    @Column(nullable = false)
//    private String password;

//    @Enumerated(EnumType.STRING)
//    private Authority authority;

    @Builder
    public TreeMember(String email, String password, Authority authority) {
        this.email = email;
//        this.password = password;
//        this.authority = authority;
    }



}
