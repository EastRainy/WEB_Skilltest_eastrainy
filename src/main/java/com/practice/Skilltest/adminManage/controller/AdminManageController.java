package com.practice.Skilltest.adminManage.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adminManage")
public class AdminManageController {

    @GetMapping({"","/"})
    public String getMainRoot(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("CurrUsername", user.getUsername());
        return "redirect:/adminManage/main";
    }
    @GetMapping("/main")
    public String getMain(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("CurrUsername", user.getUsername());
        return "html/adminManage/common/main";
    }




}
