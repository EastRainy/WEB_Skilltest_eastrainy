package com.practice.Skilltest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String rootacsess(){
        return "redirect:/main";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/main")
    public String main(Model model) {
        return "html/main";
    }


}