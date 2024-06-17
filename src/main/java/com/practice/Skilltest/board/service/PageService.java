package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {

    void calTotalPage();
    boolean checkValid(long crrPage);
    long[] pageRange(long crrPage);
    boolean haveNext(long crrPage);
    List<BoardDto> selectedPageList(long crrPage);
    List<BoardDto> selectedPageListAdmin(long crrPage);

    long crrBoardPagePosition(long id);

}
