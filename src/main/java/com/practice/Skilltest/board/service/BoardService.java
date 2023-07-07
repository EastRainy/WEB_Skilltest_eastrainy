package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.stereotype.Service;


@Service
public interface BoardService {

    BoardDto viewOne(long id);

    long newBoard(BoardDto req);

    boolean modifyBoard(BoardDto req);

    void upView(long id);

    void deleteBoard(long id);

}
