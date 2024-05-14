package com.practice.Skilltest.user.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class UserDetailDto {


    private String username;

    private String personname;
    private LocalDate birthdate;
    private String birthdate_string;
    private String phone;
    private String email;
    private String postnum;
    private String address;
    private String address_detail;

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("personname",personname);
        map.put("birthdate",birthdate);
        map.put("phone",phone);
        map.put("email",email);
        map.put("postnum",postnum);
        map.put("address",address);
        map.put("address_detail",address_detail);
        return map;
    }
}
