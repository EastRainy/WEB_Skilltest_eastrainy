package com.practice.Skilltest.board.service.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public List<BoardDto> getSelectAll() {

        List<BoardDto> resultList = boardDao.selectAll();
        Collections.reverse(resultList);

        return resultList;
    }

    @Override
    public BoardDto viewOne(long id) {

        return boardDao.viewOne(id);
    }
    /*
    @Override
    public long newBoard() {


        return ;
    }

    @Override
    public boolean modifyBoard(BoardDto req) {



        return ;
    }
    */

}

