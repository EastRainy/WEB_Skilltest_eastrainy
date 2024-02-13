package com.practice.Skilltest.board.controller;

import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import com.practice.Skilltest.board.service.PageService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private final BoardService boardService;
    @Autowired
    private final PageService pageService;

    @RequestMapping(method = RequestMethod.GET, path = "/board")
    public String board(){
        return "redirect:/board/1";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/board/{page}")
    public String viewPage(@PathVariable("page") long page, Model model){

        if(!pageService.checkValid(page)){return "error/400";}
        //올바른 페이지가 아니라면 에러처리

        model.addAttribute("resultList",pageService.selectedPageList(page));
        long[] pageRange = pageService.pageRange(page);
        model.addAttribute("startRange",pageRange[0]);
        model.addAttribute("endRange",pageRange[1]);
        model.addAttribute("crrPage", page);
        model.addAttribute("haveNext", pageService.haveNext(page));
        //페이징 처리

        return "html/board/boardmain";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/board/view/{id}")
    public String viewBoard(@PathVariable("id") long id, Model model){

        try {
            model.addAttribute("result", boardService.viewOne(id));
            model.addAttribute("id", id);
            //해당 id의 게시물을 조회
        }
        catch (Exception e){
            return "error/404";
        }

        return "html/board/boardview";
    }


    //단순신규생성
    @RequestMapping(method = RequestMethod.GET, path = "/board/new")
    public String newBoardGet(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("CurrUsername", user.getUsername());

        return "html/board/boardnew";
    }
    @PostMapping(path = "/board/new")
    @ResponseBody
    public ResponseEntity<?> newBoardPost(BoardDto req){
        HttpHeaders h = new HttpHeaders();
        long dest = boardService.newBoard(req);
        //req를 이용하여 새로운 게시글 생성 서비스 이용, 리턴값은 새로 생성된 게시글의 id

        //새로 만들어진 게시글의 id를 이용하여 URI 생성하여 게시글 내용으로 이동
        h.setLocation(URI.create("/board/view/"+dest));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }
    //기존수정 페이지 접근
    @GetMapping(path = "/board/{id}/modifying")
    public String modifyingBoardGet(@PathVariable("id") long id, Model model){

        try {
            BoardDto result = boardService.viewOne(id);

            model.addAttribute("id", id);
            model.addAttribute("req", result);
        }
        catch (NotFoundException e){
            return "error/404";
        }
        catch(Exception e){
            return "error/403";
        }

        //변경을 위해 데이터를 받아와 모델에 넣고 전송
        
        return "html/board/boardmodifying";
    }
    //기존 수정 페이지 데이터 적용
    @PostMapping(path ="/board/{id}/modifying")
    @ResponseBody
    public ResponseEntity<?> modifyingBoardPost(@PathVariable("id") long id, BoardDto req, Model model){

        req.setBoard_id(id);
        boardService.modifyBoard(req);

        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("/board/view/"+id));
        
        
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        //변경된 게시물의 게시글로 자동 이동
    }


    //게시글 삭제
    @GetMapping(path="/board/{id}/delete")
    public String deleteBoard(@PathVariable("id") long id){
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
