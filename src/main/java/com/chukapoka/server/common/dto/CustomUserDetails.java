package com.chukapoka.server.common.dto;

import com.chukapoka.server.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * CustomUser 클래스는 Spring Security에서 제공하는 User 클래스를 확장하여 추가적인 사용자 정보를 저장하기 위한 클래스
 * 주로 사용자의 고유한 식별자(ID)를 추가로 저장하고자 할 때 사용
 */
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    // OAuth 로그인
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getAuthorities()));
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
        return user.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("id");
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    };

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
