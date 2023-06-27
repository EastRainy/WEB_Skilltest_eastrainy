package com.practice.Skilltest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //SpringSecurity 설정 Bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()//CSRF 공격 방어 중지
                .formLogin().loginPage("/login")//로그인페이지 설정
                .usernameParameter("id")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")//로그인 성공 이후 페이지
                .and()
                //권한설정
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and().build();
    }


    //비밀번호 암호화 객체 생성 Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
