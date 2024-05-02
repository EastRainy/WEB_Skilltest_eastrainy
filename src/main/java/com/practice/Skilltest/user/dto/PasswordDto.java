package com.practice.Skilltest.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class PasswordDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min=10,max=30,message = "비밀번호는 10자 이상 30자 이하의 길이를 가져야 합니다.")
    @Pattern(regexp = "^[a-zA-Z\\d!@#$%^&*()?_=+<>;:~.,`\\[\\]\\-]*$", message = "비밀번호에 허용되지 않는 문자가 포함되어 있습니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()?_=+<>;:~.,`\\[\\]\\-]).*$", message = "비밀번호엔 영문 소문자, 대문자, 특수문자가 각각 한 개 이상 포함되어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String password_check;

}
