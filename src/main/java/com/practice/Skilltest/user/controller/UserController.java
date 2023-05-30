package com.practice.Skilltest.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {


    @GetMapping("/login")
    public String login(){
        return "html/user/login";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "html/user/welcome";
    }


}
