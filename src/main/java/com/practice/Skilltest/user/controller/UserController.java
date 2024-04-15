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
    //우선 기존 구현된 코드로 테스트
    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<String> signup_post(@RequestBody @Validated UserSignupEntity user,
                                            BindingResult bindingResult, Model model){

        StringBuilder messageBuilder = new StringBuilder();

        JsonObject jo = new JsonObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //입력 Validation 검증 실패시

        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for(ObjectError e : errorList){
                messageBuilder.append(e.getDefaultMessage());
                messageBuilder.append("\n");
            }
            jo.addProperty("responseMessage", messageBuilder.toString());
            jo.addProperty("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
            //System.out.println("서버 응답 : "+jo.toString());
            return new ResponseEntity<>(jo.toString(), headers, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            userService.signupUser(user);
        }
        catch(Exception e){
            messageBuilder.append(e.getMessage());
            jo.addProperty("responseMessage", messageBuilder.toString());
            jo.addProperty("status", HttpStatus.BAD_REQUEST.value());
            //System.out.println("서버 응답 : "+jo.toString());
            return new ResponseEntity<>(jo.toString(),headers, HttpStatus.BAD_REQUEST);
        }

        jo.addProperty("responseMessage", "회원가입 성공");
        jo.addProperty("status", HttpStatus.CREATED.value());
        return new ResponseEntity<>(jo.toString(), headers, HttpStatus.CREATED);

    }

    //회원가입 성공 페이지--------------------
    @GetMapping("/signupSuccess")
    public String signup_success(){
        return "html/user/signupSuccess";
    }


    @GetMapping("/mypage")
    public String mypage_get(){
        return "/html/user/mypage";
    }

}
