package com.practice.Skilltest.board.controller;

import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

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
        model.addAttribute("id",id);
        return "html/board/boardview";
    }
    //단순신규생성
    @RequestMapping(method = RequestMethod.GET, path = "/board/new")
    public String newBoardGet(){
        return "html/board/boardnew";
    }
    @RequestMapping(method = RequestMethod.POST, path = "/board/new")
    @ResponseBody
    public String newBoardPost(){

        return "redirect:/board";
    }
    //기존수정
    @RequestMapping(method = RequestMethod.GET, path = "/board/{id}/modifying")
    public String modifyingBoardGet(@PathVariable("id") long id, Model model){

        BoardDto result = boardService.viewOne(id);

        model.addAttribute("title");
        model.addAttribute("writer");
        model.addAttribute("content");
        return "html/board/boardmodifying";
    }
    @RequestMapping(method = RequestMethod.POST, path ="/board/{id}/modifying")
    @ResponseBody
    public String modifyingBoardPost(@PathVariable("id") long id, Model model){



        return "redirect:/board/";
    }

}
