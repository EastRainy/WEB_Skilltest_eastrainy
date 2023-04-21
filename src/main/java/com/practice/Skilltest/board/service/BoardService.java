package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface BoardService {

    List<BoardDto> selectAll();

}
