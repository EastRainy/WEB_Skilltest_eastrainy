package com.practice.Skilltest.board.controller;

import com.google.gson.JsonObject;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.dto.BoardIdCarrier;
import com.practice.Skilltest.board.service.BoardService;
import com.practice.Skilltest.board.service.PageService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {


    private final BoardService boardService;
    private final PageService pageService;

    //메인 보드 접근시 첫번째 페이지로 리다이렉트
    @RequestMapping(method = RequestMethod.GET, path = "/board")
    public String board(){
        return "redirect:/board/1";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/board/{page}")
    public String viewPage(@PathVariable("page") long page, Model model){

        if(!pageService.checkValid(page)){return "error/400";}
        //올바른 페이지가 아니라면 에러처리

        //timestamp 형 자료 LocalDate 형으로 변경
        List<BoardDto> dtos = pageService.selectedPageList(page);
        Timestamp stmp;

        for (BoardDto boardDto : dtos) {

            stmp = boardDto.getCreated_time();

            //if(stmp.toLocalDateTime().== LocalDateTime.now().get)


        }

        System.out.println(dtos.get(0).toString());

        model.addAttribute("resultList", dtos);
        long[] pageRange = pageService.pageRange(page);
        model.addAttribute("startRange",pageRange[0]);
        model.addAttribute("endRange",pageRange[1]);
        model.addAttribute("crrPage", page);
        model.addAttribute("haveNext", pageService.haveNext(page));
        //페이징 처리에 필요한 속성 추가

        return "html/board/boardmain";
    }

    //메뉴 복귀 시 해당 게시물이 위치한 페이지를 확인 API
    @ResponseBody
    @PostMapping("/board/pagerequest/")
    public ResponseEntity<String> returnPageById(@RequestBody BoardIdCarrier idCarrier){

        JsonObject jo = new JsonObject();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        jo.addProperty("destination", pageService.crrBoardPagePosition(idCarrier.getId()));

        return new ResponseEntity<>(jo.toString(), header, HttpStatus.OK);
    }


    //게시물 조회 GET
    @RequestMapping(method = RequestMethod.GET, path = "/board/view/{id}")
    public String viewBoard(@PathVariable("id") long id, Model model, @AuthenticationPrincipal User user){

        try {
            model.addAttribute("result", boardService.viewOne(id));
            model.addAttribute("id", id);
            //해당 id의 게시물을 조회 시도
            if(boardService.checkValidRequester(id, user.getUsername(), user.getAuthorities())){
                model.addAttribute("modifiable",true);
            }//해당 게시물의 작성자 혹은 수정권한이 있는 경우 수정여부 속성 추가
        }
        catch (Exception e){
            return "error/404";
            //없을 시 404에러
        }

        return "html/board/boardview";
    }


    //단순 신규 게시물 생성 GET
    @GetMapping(path = "/board/new")
    public String newBoardGet(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("CurrUsername", user.getUsername());

        return "html/board/boardnew";
    }
    //단순 신규 게시물 생성 POST
    @PostMapping(path = "/board/new")
    @ResponseBody
    public ResponseEntity<?> newBoardPost(BoardDto req){
        HttpHeaders h = new HttpHeaders();
        long dest;

        try{
            dest = boardService.newBoard(req);
        }
        catch (Exception e){
            h.setLocation(URI.create("/board"));
            return new ResponseEntity<>(h, HttpStatus.BAD_REQUEST);
        }

        //req를 이용하여 새로운 게시글 생성 서비스 이용, 리턴값은 새로 생성된 게시글의 id

        //새로 만들어진 게시글의 id를 이용하여 URI 생성하여 게시글 내용으로 이동
        h.setLocation(URI.create("/board/view/"+dest));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }


    //수정 페이지 GET, try-catch로 에러처리 시도
    @GetMapping(path = "/board/modifying/{id}")
    public String modifyingBoardGet(@PathVariable("id") long id, Model model, @AuthenticationPrincipal User user){

        try {
            if(!boardService.checkValidRequester(id, user.getUsername(), user.getAuthorities())){
                throw new Exception("Not Valid to modify");
            }
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
    //기존 수정 페이지 POST
    @PostMapping(path ="/board/modifying/{id}")
    @ResponseBody
    public ResponseEntity<?> modifyingBoardPost(@PathVariable("id") long id, BoardDto req, @AuthenticationPrincipal User user){
        HttpHeaders h = new HttpHeaders();

        req.setBoard_id(id);
        if(!boardService.modifyBoard(req, user.getUsername(), user.getAuthorities())){
            h.setLocation(URI.create("error/403"));
            return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        }

        h.setLocation(URI.create("/board/view/"+id));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        //변경된 게시물의 게시글로 자동 이동
    }


    //게시글 삭제
    @GetMapping(path="/board/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteBoard(@PathVariable("id") long id, @AuthenticationPrincipal User user){
        HttpHeaders h = new HttpHeaders();

        if(!boardService.deleteBoard(id, user.getUsername(), user.getAuthorities())){
            h.setLocation(URI.create("error/403"));
            return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        }

        h.setLocation(URI.create("/board"));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }
}
