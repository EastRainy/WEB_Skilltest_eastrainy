package com.practice.Skilltest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @GetMapping("/")
    public String rootacsess(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(){
        return "html/main.html";
    }



}
