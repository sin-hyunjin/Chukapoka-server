package com.chukapoka.server.common.authority;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Spring Security 6.1.0부터는 메서드 체이닝의 사용을 지양하고 람다식을 통해 함수형으로 설정하게 지향함
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/api/user/**").authenticated() // 토큰 없이 접근 가능하다는 뜻
                                .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin.loginPage("/login")
                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                );



//        http.
//    httpBasic(httpBasic -> httpBasic.disable()).
//                csrf(csrf -> csrf.disable()).
//    authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll());

        return http.build();
    }




}
