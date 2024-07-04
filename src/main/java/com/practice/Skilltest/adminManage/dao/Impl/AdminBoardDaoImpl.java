package com.practice.Skilltest.adminManage.dao.Impl;

import com.practice.Skilltest.adminManage.dao.AdminManageBoardDao;
import com.practice.Skilltest.board.dao.impl.BoardDaoImpl;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.dto.HideRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminBoardDaoImpl implements AdminManageBoardDao {

    private final SqlSession sqlSession;
    public AdminBoardDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int checkById(long id) {return sqlSession.selectOne("checkByIdAdmin", id); }

    @Override
    public String getWriter(long id) {
        return sqlSession.selectOne("gerWriterAdmin", id);
    }

    @Override
    public BoardDto viewOne(long id) {
        return sqlSession.selectOne("viewOneAdmin", id);
    }

    @Override
    public long newBoard(BoardDto req) {
        return sqlSession.insert("newBoardAdmin", req);
    }

    @Override
    public boolean updateBoard(Map<String, Object> map) {
        return 1==sqlSession.update("updateBoardAdmin",map);
    }

    @Override
    public void updateViewcount(long id) {
        sqlSession.update("updateViewcountAdmin", id);
    }

    @Override
    public boolean deleteBoard(long id) {
        return 1==sqlSession.delete("deleteBoardAdmin", id);
    }

    @Override
    public boolean updateHide(HideRequestDto hideRequestDto) {
        return 1==sqlSession.update("updateHideAdmin", hideRequestDto);
    }

    @Override
    public long selectCount() {
        return sqlSession.selectOne("selectCountAdmin");
    }

    @Override
    public long selectNewer(long id) {
        return sqlSession.selectOne("selectNewerAdmin", id);
    }

    @Override
    public List<BoardDto> selectPageRange(long offset) {
        return sqlSession.selectList("selectPageRangeAdmin", offset);
    }
}
