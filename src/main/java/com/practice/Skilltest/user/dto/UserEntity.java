package com.practice.Skilltest.user.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;


@NoArgsConstructor//UserEntity() 자동생성
@AllArgsConstructor//UserEntity(username,password)자동생성
@Component
@Setter
@Getter
public class UserEntity {


    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;


}
