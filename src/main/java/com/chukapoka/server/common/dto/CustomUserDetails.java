package com.chukapoka.server.common.dto;

import com.chukapoka.server.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails 클래스는 Spring Security에서 제공하는 User 클래스를 확장하여 추가적인 사용자 정보를 저장하기 위한 클래스
 * 주로 사용자의 고유한 식별자(ID)를 추가로 저장하고자 할 때 사용
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    /** 일반 로그인 */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user == null) {
            return Collections.emptyList(); // user가 null인 경우 빈 권한 목록 반환
        }
        return Collections.singleton(new SimpleGrantedAuthority(user.getAuthorities()));
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (user != null) {
            return user.getId().toString();
        }
        return null; // 사용자 객체가 null인 경우 null 반환
    }


    /**  계정의 만료 여부 반환 (기한이 없으므로 항상 true 반환) */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    };
    /** 계정의 잠금 여부 반환 (잠금되지 않았으므로 항상 true 반환)*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /** 자격 증명의 만료 여부 반환 (기한이 없으므로 항상 true 반환)*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /** 계정의 활성화 여부 반환 (활성화된 계정이므로 항상 true 반환)*/
    @Override
    public boolean isEnabled() {
        return true;
    }


}
