package com.practice.Skilltest.board.dao.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class BoardDaoImpl implements BoardDao {

    private final SqlSession sqlSession;
    @Autowired
    public BoardDaoImpl(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    @Override
    public List<BoardDto> selectAll() {
        return sqlSession.selectList("selectAll");
    }

    @Override
    public BoardDto viewOne(long id) {
        return sqlSession.selectOne("viewOne", id);
    }

    @Override
    public long newBoard(BoardDto req) {
        return sqlSession.insert("newBoard", req);
    }

    @Override
    public boolean updateBoard(Map<String, Object> map) {
        return 1==sqlSession.update("updateBoard",map);
    }

    @Override
    public void updateViewcount(long id) {
        sqlSession.update("updateViewcount", id);
    }

    @Override
    public boolean deleteBoard(long id) {
        return 1==sqlSession.delete("deleteBoard", id);
    }


}
