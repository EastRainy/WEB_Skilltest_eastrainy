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

    public long newBoard(BoardDto req) {

        boardDao.newBoard(req);

        System.out.println("id: " + (long) req.getBoard_id());

        return req.getBoard_id();
    }

    @Override
    public boolean modifyBoard(BoardDto req) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", req.getBoard_id());
        map.put("title",req.getTitle());
        map.put("content", req.getContent());

        return boardDao.updateBoard(map);
    }

    @Override
    public void deleteBoard(long id) {
        boardDao.deleteBoard(id);
    }
}

