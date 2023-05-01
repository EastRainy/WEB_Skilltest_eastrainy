package com.practice.Skilltest.board.dao;

import com.practice.Skilltest.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {
    List<BoardDto> selectAll();
    BoardDto viewOne(long id);
    long newBoard(Map<String, Object> map);
    boolean updateBoard(Map<String, Object> map);
    boolean deleteBoard(long id);


}
