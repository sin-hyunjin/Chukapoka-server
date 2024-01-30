package com.chukapoka.server.common.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * CustomUser 클래스는 Spring Security에서 제공하는 User 클래스를 확장하여 추가적인 사용자 정보를 저장하기 위한 클래스
 * 주로 사용자의 고유한 식별자(ID)를 추가로 저장하고자 할 때 사용
 */
@Getter
public class CustomUser extends User {
    private final String email;

    public CustomUser(String userId, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
        this.email = email;
    }

}
