package com.practice.Skilltest.user.controller;



import com.google.gson.JsonObject;
import com.practice.Skilltest.user.dto.UserEntity;
import com.practice.Skilltest.user.dto.UserSignupEntity;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserServiceImpl userService;

    //로그인 페이지 요청--------------------------------
    @GetMapping("/login")
    public String login_get(Model model){
        model.addAttribute("user", new UserEntity());
        return "html/user/login";
    }
    //로그인 시 에러 처리 할 경우--------------------------
    @PostMapping("/login")
    public String login_post(Model model, HttpServletRequest request){

        model.addAttribute("message",request.getAttribute("message"));

        model.addAttribute("user", new UserEntity());

        return "html/user/login";
    }
    
    //회원가입 페이지 요청---------------------
    @GetMapping("/signup")
    public String signup_get(Model model){
        model.addAttribute("user", new UserSignupEntity());
        return "html/user/signup";
    }

    //회원가입 신청 응답----------------------
    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<String> signup_post(@RequestBody @Validated UserSignupEntity user,
                                      BindingResult bindingResult, Model model){

        StringBuilder errorMessage = new StringBuilder();
        JsonObject jo = new JsonObject();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println("회원가입 접근");
        //입력 Validation 검증 실패시
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for(ObjectError e : errorList){
                errorMessage.append(e.getDefaultMessage());
                errorMessage.append("\n");
            }
            jo.addProperty("responseMessage", errorMessage.toString());
            System.out.println("서버 응답 : "+jo.toString());
            return new ResponseEntity<>(jo.toString(), headers, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        System.out.println("회원가입 진행");
        try {
            userService.signupUser(user);
        }
        catch(Exception e){
            errorMessage.append(e.getMessage());
            jo.addProperty("responseMessage", errorMessage.toString());
            System.out.println("서버 응답 : "+jo.toString());
            return new ResponseEntity<>(jo.toString(),headers, HttpStatus.BAD_REQUEST);
        }

        System.out.println("회원가입 성공");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }






    //회원가입 성공 페이지--------------------
    @GetMapping("/signupSuccess")
    public String signup_success(){
        return "html/user/signupSuccess";
    }

}
