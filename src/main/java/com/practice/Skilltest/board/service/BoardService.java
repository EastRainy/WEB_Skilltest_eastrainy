package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public interface BoardService {

    BoardDto viewOne(long id);

    long newBoard(BoardDto req);

    boolean modifyBoard(BoardDto req);

    void upView(long id);

    void deleteBoard(long id);

}
