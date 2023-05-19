package com.practice.Skilltest.board.service.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    //게시글 내용 조회
    @Override
    public BoardDto viewOne(long id) {
        upView(id);
        return boardDao.viewOne(id);
    }

    //새 게시글 생성
    public long newBoard(BoardDto req) {

        boardDao.newBoard(req);

        return req.getBoard_id();
    }

    //게시글 수정
    @Override
    public boolean modifyBoard(BoardDto req) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", req.getBoard_id());
        map.put("title",req.getTitle());
        map.put("content", req.getContent());

        return boardDao.updateBoard(map);
    }

    //조회수 증가
    @Override
    public void upView(long id) {
        boardDao.updateViewcount(id);
    }

    //게시글 삭제
    @Override
    public void deleteBoard(long id) {
        boardDao.deleteBoard(id);
    }
}

