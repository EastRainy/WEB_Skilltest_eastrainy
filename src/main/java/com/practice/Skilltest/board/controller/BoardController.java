package com.practice.Skilltest.board.controller;

import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @RequestMapping(method = RequestMethod.GET, path = "/board")
    public String board(Model model){

        model.addAttribute("resultList", boardService.getSelectAll());

        return "html/board/boardmain";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/board/{id}")
    public String viewBoard(@PathVariable("id") long id, Model model){

        model.addAttribute("result", boardService.viewOne(id));

        return "html/board/boardview.html";

    }




}
