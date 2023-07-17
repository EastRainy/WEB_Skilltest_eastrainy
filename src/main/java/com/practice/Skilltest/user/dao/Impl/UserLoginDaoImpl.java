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

    @Override
    public String refer_id(String id) {
        return sqlSession.selectOne("refer_id", id);
    }

    @Override
    public String refer_pw(String id) {
        return sqlSession.selectOne("refer_pw", id);
    }

    @Override
    public boolean signup_user(UserEntity userEntity) {
        return (1==sqlSession.insert("user", userEntity));
    }
}
