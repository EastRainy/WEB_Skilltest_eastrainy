package com.practice.Skilltest.board.dao.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.dto.HideRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public int checkById(long id) {return sqlSession.selectOne("checkById", id); }

    @Override
    public String getWriter(long id) {return sqlSession.selectOne("gerWriter", id);}

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

    @Override
    public boolean updateHide(HideRequestDto hideRequestDto) {
        return 1==sqlSession.update("updateHide", hideRequestDto);
    }


    @Override
    public long selectCount() {
        return sqlSession.selectOne("selectCount");
    }

    public long selectNewer(long id){ return sqlSession.selectOne("selectNewer", id); }
    @Override
    public List<BoardDto> selectPageRange(long offset) {
        return sqlSession.selectList("selectPageRange", offset);
    }


}
