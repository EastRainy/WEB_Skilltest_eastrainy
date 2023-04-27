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
    public boolean newBoard(String writer, String title, String content) {
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("writer", writer);
        map.put("title", title);
        map.put("content", content);
        return 1 == sqlSession.insert("newBoard", map);
    }

    @Override
    public boolean updateBoard(long id, String writer, String title, String content) {
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("id", id);
        map.put("writer", writer);
        map.put("title", title);
        map.put("content", content);
        return 1 == sqlSession.update("updateBoard",map);
    }

    @Override
    public boolean deleteBoard(long id) {
        return 1==sqlSession.delete("deleteBoard", id);
    }


}
