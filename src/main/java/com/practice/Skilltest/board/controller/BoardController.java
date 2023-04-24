package com.practice.Skilltest.board.controller;


import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardDao boardDao;

    @RequestMapping(method = RequestMethod.GET, path = "/board")
    public String board(Model model){
        List<BoardDto> resultList = boardDao.selectAll();

        Collections.reverse(resultList);

        model.addAttribute("resultList", resultList);

        return "html/board/boardmain";
    }

}
