package com.practice.Skilltest.user.dao.Impl;

import com.practice.Skilltest.user.dao.UserLoginDao;
import com.practice.Skilltest.user.dto.UserEntity;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserLoginDaoImpl implements UserLoginDao {


    private final SqlSession sqlSession;

    @Autowired
    public UserLoginDaoImpl(SqlSession sqlSesslin){this.sqlSession = sqlSesslin;}

    //username 기반 id, password 정보 검색
    @Override
    public String refer_id(String username) {
        return sqlSession.selectOne("refer_id", username);
    }
    @Override
    public String refer_pw(String username) {
        return sqlSession.selectOne("refer_pw", username);
    }
    
    
    //userEntity 정보로 유저 가입
    @Override
    public boolean signup_user(UserEntity userEntity) {
        return (1==sqlSession.insert("user", userEntity));
    }
    
    //해당 유저의 마지막 로그인 시간 업데이트
    @Override
    public void lastlogin_update(String username) {
        sqlSession.update("lastlogin_update", username);
    }
}
