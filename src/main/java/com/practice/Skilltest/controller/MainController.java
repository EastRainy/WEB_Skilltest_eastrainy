package com.practice.Skilltest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String rootacsess(){
        return "redirect:/main";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/main")
    public String mainPage(Model model, @AuthenticationPrincipal User user) {
        

        model.addAttribute("CurrUsername", user.getUsername());
        return "html/common/main";
    }

    @GetMapping("/welcome")
    public String getWelcome(Model model, @AuthenticationPrincipal User user){
            return "html/common/welcome";
    }

}