package com.practice.Skilltest;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{

        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver()
                .getResource("classpath:mybatis/mybatis-config.xml"));

        return sqlSessionFactory.getObject();
    }

    //SpringSecurity 설정 Bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()//CSRF 공격 방어 중지
                .formLogin().loginPage("/login")//로그인페이지 설정
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")//로그인 성공 이후 페이지
                .and()
                //권한설정
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .antMatchers("/users/**").authenticated()
                .and().build();
    }
    //비밀번호 암호화 객체 생성 Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
