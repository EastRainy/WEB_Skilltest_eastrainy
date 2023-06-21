package com.practice.Skilltest.user;

import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private UserServiceImpl userService;

    //목표 클래스를 지원하는지 확인 기능
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    //Validator 검증 로직
    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        if(ObjectUtils.isEmpty(user.getName())){
            errors.rejectValue("name", "아이디를 입력하십시오", "아이디를 입력하십시오.");
        }

        if(!ObjectUtils.isEmpty(user.getName())&&!userService.checkByUsername(user.getName())){
            errors.rejectValue("name", "이미 존재하는 아이디입니다", "이미 존재하는 아이디입니다.");
        }


    }
}
