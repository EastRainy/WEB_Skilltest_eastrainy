package com.practice.Skilltest.user.dao;

import com.practice.Skilltest.user.dto.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginDao {


    String refer_id(String username);

    String refer_pw(String username);

    boolean signup_user(UserEntity userEntity);

    void lastlogin_update(String username);

}
