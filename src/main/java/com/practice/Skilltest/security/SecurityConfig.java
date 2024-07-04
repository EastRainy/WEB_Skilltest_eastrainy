package com.practice.Skilltest.security;

import com.practice.Skilltest.security.handler.AuthFailHandler;
import com.practice.Skilltest.security.handler.AuthSuccessHandler;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private ApplicationContext applicationContext;
    //스프링 컨테이너 연결


    //SpringSecurity 설정 Bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()//CSRF 공격 방어 중지
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")//로그인페이지 설정
                    //Form login에서 Spring Security가 받아올 정보의 속성값
                    .usernameParameter("username")
                    .passwordParameter("password")
                    //성공, 실패 핸들러 설정
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(new AuthFailHandler())
                .and()
                //로그아웃 설정
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                //권한설정
                .authorizeHttpRequests()
                    .antMatchers("/login","/signup", "/signup/**", "/signupSuccess","/welcome","/navbar").permitAll()
                    .antMatchers("/css/**", "/js/**", "/img/**").permitAll()//static 경로 자료 접근 권한 이슈 해결
                    .antMatchers("/adminManage/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                .and().build();
    }

    //bean으로 만들때 스프링 컨테이너에서 AuthsuccessHandler 에 UserServiceImpl 의존성 주입
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        UserServiceImpl userService = applicationContext.getBean(UserServiceImpl.class);
        return new AuthSuccessHandler(userService);
    }

    /* fail bean 주입 필요시 사용
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        AuthFailExceptionMessage exceptionMessage = applicationContext.getBean(AuthFailExceptionMessage.class);
        return new AuthFailHandler(exceptionMessage);
    }
    */


    //비밀번호 암호화 객체 생성 Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
