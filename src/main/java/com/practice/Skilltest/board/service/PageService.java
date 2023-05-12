package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.stereotype.Service;

@Service
public interface PageService {


    void calTotalPage();
    BoardDto[] selectedPageList(long crrPage);







}
