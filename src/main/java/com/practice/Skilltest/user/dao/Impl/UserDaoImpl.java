package com.practice.Skilltest.user.dao.Impl;

import com.practice.Skilltest.user.dao.UserDao;
import com.practice.Skilltest.user.dto.UserDetailDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    private final SqlSession sqlSession;

    public UserDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public UserDetailDto getUserData(String username) {
        return sqlSession.selectOne("getMyData", username);}

    @Override
    public boolean updatePassword(Map<String, Object> inputMap) {
        return 0<sqlSession.update("updatePassword", inputMap);
    }

    @Override
    public boolean updateUserData(Map<String, Object> userDataMap) {return 0< sqlSession.update("updateUserData",userDataMap);}}
