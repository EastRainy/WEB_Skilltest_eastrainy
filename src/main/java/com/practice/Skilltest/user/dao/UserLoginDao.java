package com.practice.Skilltest.user.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserLoginDao {


    String refer_id(String username);

    String refer_pw(String username);

    //userEntity 정보로 유저 가입
    boolean signup_user(Map<String, Object> params);

    void lastlogin_update(String username);

}
