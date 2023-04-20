package com.practice.Skilltest.controller;

import com.practice.Skilltest.dto.TestDto;
import com.practice.Skilltest.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class PageController {

    private final TestService testService;
    @RequestMapping("/")
    public String rootacsess(){
        return "redirect:/main";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/main")
    public String main(Model model) {
        List<TestDto> result = testService.testing();

        System.out.println(result.toString());

        //model.addAttribute("message", result.toString());

        return "html/main";
    }
}
