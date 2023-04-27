package com.practice.Skilltest.board.controller;

import com.practice.Skilltest.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

        return "html/board/boardview";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/board/new")
    public String newBoardGet(){
        return "html/board/boardnew";
    }//단순신규생성
    @RequestMapping(method = RequestMethod.POST, path = "/board/new")
    public String newBoardPost(){

    }
    @RequestMapping(method = RequestMethod.GET, path = "/board/{id}/modifying")
    public String modifyingBoard(@PathVariable("id") long id, Model model){
        
        return "html/board/boardmodifying";
    }//기존수정




}
