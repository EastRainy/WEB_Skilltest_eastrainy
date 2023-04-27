package com.practice.Skilltest.board.dao;

import com.practice.Skilltest.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface BoardDao {
    List<BoardDto> selectAll();
    BoardDto viewOne(long id);
    boolean newBoard(String writer, String title, String content);
    boolean updateBoard(long id, String writer, String title, String content);
    boolean deleteBoard(long id);

}
