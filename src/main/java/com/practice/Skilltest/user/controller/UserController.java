package com.practice.Skilltest.user.controller;


import com.practice.Skilltest.user.dto.UserEntity;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;


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
        model.addAttribute("user", new UserEntity());
        return "html/user/signup";
    }


    @PostMapping("/signup")
    public String signup_post(UserEntity user){

        userService.signupUser(user);
        return "redirect:/login";
    }


}
