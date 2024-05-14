package com.practice.Skilltest.user.controller;



import com.google.gson.JsonObject;
import com.practice.Skilltest.user.dto.PasswordDto;
import com.practice.Skilltest.user.dto.UserDetailDto;
import com.practice.Skilltest.user.dto.UserEntity;
import com.practice.Skilltest.user.dto.UserDto;
import com.practice.Skilltest.user.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
public class UserController {

    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

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
        model.addAttribute("user", new UserDto());
        return "html/user/signup";
    }

    //회원가입 신청 응답----------------------
    //우선 기존 구현된 코드로 테스트
    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<String> signup_post(@RequestBody @Validated UserDto user,
                                            BindingResult bindingResult, Model model){

        StringBuilder messageBuilder = new StringBuilder();

        //응답을 위한 json오브젝트
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
            jo.addProperty("status", HttpStatus.CONFLICT.value());
            //System.out.println("서버 응답 : "+jo.toString());
            return new ResponseEntity<>(jo.toString(), headers, HttpStatus.CONFLICT);
        }

        try {
            userService.signupUser(user);
        }
        catch(Exception e){ // 처리 중 에러 발생 시
            messageBuilder.append(e.getMessage());
            jo.addProperty("responseMessage", messageBuilder.toString());
            jo.addProperty("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(jo.toString(),headers, HttpStatus.BAD_REQUEST);
        }
        
        //에러 없이 회원가입 성공시
        jo.addProperty("responseMessage", "회원가입 성공");
        jo.addProperty("status", HttpStatus.CREATED.value());
        return new ResponseEntity<>(jo.toString(), headers, HttpStatus.CREATED);

    }

    //회원가입시 아이디 중복여부 체크 응답
    @PostMapping(value = "/signup/checkusername")
    @ResponseBody
    public ResponseEntity<String> checkUsername(@RequestBody Map<String, String> requestBody){

        String username = requestBody.get("username");


        HttpHeaders headers = new HttpHeaders();
        JsonObject jo = new JsonObject();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(userService.checkByUsername(username)){
            jo.addProperty("usable","true");
        }else{
            jo.addProperty("usable","false");
        }
        jo.addProperty("status",HttpStatus.OK.value());


        return new ResponseEntity<>(jo.toString(), headers, HttpStatus.OK);

    }


    //회원가입 성공 페이지--------------------
    @GetMapping("/signupSuccess")
    public String signup_success(){
        return "html/user/signupSuccess";
    }

    //MyPage GET 조회 접근
    @GetMapping("/mypage")
    public String mypage_get(Model model, @AuthenticationPrincipal User user){

        UserDetailDto userData = userService.getUserDetails(user);
        model.addAttribute("userData", userData);

        return "/html/user/mypage";
    }
    //MyPage 수정 페이지
    @GetMapping("/mypage/update")
    public String mypage_update(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("userData", userService.getUserDetails(user));
        return "html/user/mypageUpdate";
    }

    //MyPage 수정 요청이 왔을 시
    @PostMapping("/mypage/update")
    @ResponseBody
    public ResponseEntity<String> mypage_post(@AuthenticationPrincipal User user,
                                              @RequestBody @Validated UserDetailDto userData){

        /*TODO MyPage를 통해 유저 데이터를 변경하는 경우 처리
         *  1. 입/출력 및 데이터베이스 변경 로직 확인
         *  2. Validation 처리
         *  3. 주소 API 등 타 API 기능 도입 이후 최종 여부 확인
         * 현재 검증없음 및 코드처리형식을 비밀번호 변경과 비슷하게 처리 예정
         * */
        

        JsonObject jo = new JsonObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            boolean success = userService.updateUserData(userData, user.getUsername());
                if (success) {
                        jo.addProperty("responseMessage", "유저 정보 수정 성공");
                        jo.addProperty("status", HttpStatus.OK.value());
                        return new ResponseEntity<>(jo.toString(), headers, HttpStatus.CREATED);

                } else {
                    jo.addProperty("responseMessage", "유저 정보 수정 실패");
                    jo.addProperty("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    log.info("데이터 변경 실패");
                    return new ResponseEntity<>(jo.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
                }
        } catch (Exception e) {
            log.info(e.getMessage());
            jo.addProperty("responseMessage", e.getMessage());
            jo.addProperty("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(jo.toString(), headers, HttpStatus.BAD_REQUEST);
        }

    }
    //비밀번호 변경 POST
    @PostMapping("/mypage/update/password")
    @ResponseBody
    public ResponseEntity<String> mypage_passwordChange(@AuthenticationPrincipal User user,
                                                        @RequestBody @Validated PasswordDto passwordDto,
                                                        BindingResult bindingResult){


        JsonObject jo = new JsonObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        //서비스별로 validate 한번 더 서버단에서 체크 후
        //이상이 있을 시 json에 해당부분 전송하여 응답할 수 있도록 구성
        //이상이 없는 경우 반영하고 성공 전송

        try {
            Map<String, Object> output = userService.updateUserPassword(user, passwordDto, bindingResult);

            if ((boolean) output.get("updated")) {
                jo.addProperty("status", HttpStatus.OK.value());
                jo.addProperty("responseMessage", "success");

                return new ResponseEntity<>(jo.toString(), headers, HttpStatus.OK);
            } else {
                jo.addProperty("status", HttpStatus.CONFLICT.value());
                jo.addProperty("responseMessage", (String) output.get("message"));

                return new ResponseEntity<>(jo.toString(), headers, HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            log.error(e.getStackTrace());
            jo.addProperty("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

            return new ResponseEntity<>(jo.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
