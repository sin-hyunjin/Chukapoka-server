package com.chukapoka.server.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tb_user")
public class User {

    /**
     * Security Test를 위한 사용자 도메인
     * 이메일, 비밀번호, 권한만을 가지고 있으며, 이메일로 로그인하도록 만들 예정
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = true)
    private String emailType;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Builder
    public User(String email, String password, String emailType) {
        this.email = email;
        this.password = password;
        this.emailType = emailType;
        this.updatedAt = LocalDateTime.now();
    }



}
