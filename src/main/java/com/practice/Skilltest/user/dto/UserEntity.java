package com.practice.Skilltest.user.dto;

import lombok.*;
import org.springframework.stereotype.Component;


@Data//게터세터,equal,hashcode 자동생성
@NoArgsConstructor//UserEntity() 자동생성
@AllArgsConstructor//UserEntity(username,password)자동생성
@Component
public class UserEntity {

    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
