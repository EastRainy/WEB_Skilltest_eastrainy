package com.practice.Skilltest.user.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotBlank;


@Data//게터세터,equal,hashcode 자동생성
@NoArgsConstructor//UserEntity() 자동생성
@AllArgsConstructor//UserEntity(username,password)자동생성
@Component
public class UserEntity {


    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;


}
