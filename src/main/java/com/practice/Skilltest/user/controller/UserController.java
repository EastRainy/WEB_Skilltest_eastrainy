package com.practice.Skilltest.user.controller;


import com.practice.Skilltest.user.dto.UserEntity;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String login_get(Model model, @RequestParam(value="String", required=false, defaultValue="") String message){
        model.addAttribute("user", new UserEntity());
        return "html/user/login";
    }

    @GetMapping("/signup")
    public String signup_get(Model model){
        model.addAttribute("user", new UserEntity());
        return "html/user/signup";
    }

    @PostMapping("/signup")
    public String signup_post(UserEntity user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.signupUser(user);
        return "redirect:/login";
    }


}
