package com.practice.Skilltest.user.dao;

import com.practice.Skilltest.user.dto.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginDao {


    String refer_id(String id);

    String refer_pw(String id);

    boolean signup_user(UserEntity userEntity);

}
