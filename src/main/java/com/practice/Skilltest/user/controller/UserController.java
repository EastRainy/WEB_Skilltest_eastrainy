package com.practice.Skilltest.user.controller;


import com.practice.Skilltest.user.dto.UserEntity;
import com.practice.Skilltest.user.dto.UserSignupEntity;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserServiceImpl userService;

    //로그인 페이지 요청
    @GetMapping("/login")
    public String login_get(Model model){
        model.addAttribute("user", new UserEntity());
        return "html/user/login";
    }
    //로그인 시 에러 처리 할 경우
    @PostMapping("/login")
    public String login_post(Model model, HttpServletRequest request){

        model.addAttribute("message",request.getAttribute("message"));

        model.addAttribute("user", new UserEntity());

        return "html/user/login";
    }
    
    //회원가입 페이지 요청
    @GetMapping("/signup")
    public String signup_get(Model model){
        model.addAttribute("user", new UserSignupEntity());
        return "html/user/signup";
    }

    //회원가입 신청 검증
    @PostMapping(value = "/signup")
    public String signup_post(@Validated UserSignupEntity user,
                              BindingResult bindingResult, Model model){

        //입력 Validation 검증 실패시
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            String errorMessage = errorList.get(0).getDefaultMessage();
            model.addAttribute("announce_bottom",errorMessage);
            model.addAttribute("user", new UserSignupEntity());
            return "html/user/signup";
        }

        System.out.println("회원가입 진행");
        try {
            userService.signupUser(user);
        }
        catch(Exception e){
            model.addAttribute("announce_bottom", e.getMessage());
            model.addAttribute("user", new UserSignupEntity());
            return "html/user/signup";
        }

        System.out.println("회원가입 성공");
        return "redirect:/signupSuccess";
    }

    //회원가입 성공 페이지
    @GetMapping("/signupSuccess")
    public String signup_success(){
        return "html/user/signupSuccess";
    }


}
