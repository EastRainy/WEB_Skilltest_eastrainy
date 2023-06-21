package com.practice.Skilltest.user.controller;


import com.practice.Skilltest.user.dto.UserDto;
import lombok.RequiredArgsConstructor;


import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {



    @GetMapping("/login")
    public String login_get(Model model){
        model.addAttribute("user", new UserDto());
        return "html/user/login";
    }

    @PostMapping("/login")
    public String login_post(User user){



        return null;
    }






    @GetMapping("/signup")
    public String signup_get(Model model){
        model.addAttribute("user", new UserDto());
        return "html/user/signup";
    }

    @PostMapping("/signup")
    public String signup_post(User user){

        


        return null;
    }


}
