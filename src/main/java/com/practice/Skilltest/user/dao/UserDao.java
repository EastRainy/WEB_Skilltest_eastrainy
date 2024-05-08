package com.practice.Skilltest.user.dao;

import com.practice.Skilltest.user.dto.UserDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserDao {

    //유저 mypage 접근 시 필요 데이터 조회
    UserDetailDto getUserData(String username);

    //비밀번호 변경
    boolean updatePassword(Map<String, Object> inputMap);

    //user_private_data update
    boolean updateUserData(Map<String, Object> userDataMap);

}
