package com.practice.Skilltest.controller;

import com.practice.Skilltest.dto.TestDto;
import com.practice.Skilltest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class PageController {
    private final TestService testService;

    @GetMapping("/")
    public String rootacsess(){
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(){
        List<TestDto> result = testService.testing();

        System.out.println(result.toString());

        return "html/main.html";
    }



}
