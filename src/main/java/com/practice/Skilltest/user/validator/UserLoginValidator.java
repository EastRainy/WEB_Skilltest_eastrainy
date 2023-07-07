package com.practice.Skilltest.user.validator;
import com.practice.Skilltest.user.dto.UserDto;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserLoginValidator implements Validator {

    //로그인용
    
    private UserServiceImpl userService;

    //목표 클래스를 지원하는지 확인 기능
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    //Validator 검증 로직
    @Override
    public void validate(Object target, Errors errors) {

        UserDto userDto = (UserDto) target;

        if(ObjectUtils.isEmpty(userDto.getId())){
            errors.rejectValue("id", "Login_idEmpty", "아이디를 입력하십시오.");
        }
        if(ObjectUtils.isEmpty(userDto.getPassword())){
            errors.rejectValue("password", "Login_pwEmpty", "비밀번호를 입력하십시오.");
        }



    }
}
