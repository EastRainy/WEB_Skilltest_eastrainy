package com.practice.Skilltest.board.dao;

import com.practice.Skilltest.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {

    //board
    List<BoardDto> selectAll();
    BoardDto viewOne(long id);
    long newBoard(BoardDto req);
    boolean updateBoard(Map<String, Object> map);
    void updateViewcount(long id);
    boolean deleteBoard(long id);

    //page

    long selectCount();


}
