package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    List<BoardDto> getSelectAll();

    BoardDto viewOne(long id);

}
