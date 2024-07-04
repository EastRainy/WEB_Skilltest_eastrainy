package com.practice.Skilltest.adminManage.controller;

import com.google.gson.JsonObject;
import com.practice.Skilltest.adminManage.service.Impi.AdminBoardServiceImpl;
import com.practice.Skilltest.adminManage.service.Impi.AdminPageServiceImpl;
import com.practice.Skilltest.board.controller.BoardController;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.dto.BoardIdCarrier;
import com.practice.Skilltest.board.dto.HideRequestDto;
import com.practice.Skilltest.board.service.BoardService;
import com.practice.Skilltest.board.service.PageService;
import com.practice.Skilltest.user.role.UserRoles;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/adminManage/board")
public class AdminBoardController {

    final PageService pageService;
    final BoardService boardService;

    public AdminBoardController(@Qualifier("AdminPageService") AdminPageServiceImpl pageService,
                                @Qualifier("AdminBoardService") AdminBoardServiceImpl boardService) {
        this.pageService = pageService;
        this.boardService = boardService;
    }

    @GetMapping({"", "/"})
    public String getBoardRoot(){
        return "redirect:/adminManage/board/1";
    }

    @GetMapping("/{page}")
    public String getBoardMain(@PathVariable("page") long page, Model model, @AuthenticationPrincipal User user){


        List<BoardDto> dtos;
        if(!pageService.checkValid(page)){return "error/400";}
        //올바른 페이지가 아니라면 에러처리

        if(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.ADMIN.getValue()))){
            dtos = pageService.selectedPageListAdmin(page);
        }//어드민이면 숨긴글 데이터 제공
        else {
                dtos = pageService.selectedPageList(page);
        }

        model.addAttribute("resultList", dtos);
        long[] pageRange = pageService.pageRange(page);
        model.addAttribute("startRange",pageRange[0]);
        model.addAttribute("endRange",pageRange[1]);
        model.addAttribute("crrPage", page);
        model.addAttribute("haveNext", pageService.haveNext(page));
        //페이징 처리에 필요한 속성 추가

        return "html/adminManage/board/manageBoardMain";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/view/{id}")
    public String viewBoard(@PathVariable("id") long id, Model model, @AuthenticationPrincipal User user){

        boolean isAdmin = user.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.ADMIN.getValue()));

        try {
            //해당 id의 게시물을 조회 시도
            BoardDto result = boardService.viewOne(id);
            //숨김 처리 글에 비정상 접근 시 잘못된 접근 처리
            if(result.is_hide() && !isAdmin){
                return "error/500";
            }
            //결과물 모델에 적재
            model.addAttribute("result", result);
            model.addAttribute("id", id);

            if(boardService.checkValidRequester(id, user.getUsername(), user.getAuthorities())){
                model.addAttribute("modifiable",true);
            }//해당 게시물의 작성자 혹은 수정권한이 있는 경우 수정여부 속성 추가
        }
        catch (Exception e){
            log.info(e.toString());
            return "error/404";
            //없을 시 404에러
        }

        return "html/adminManage/board/manageBoardView";
    }

    //메뉴 복귀 시 해당 게시물이 위치한 페이지를 확인 API
    @ResponseBody
    @PostMapping("/pagerequest/")
    public ResponseEntity<String> returnPageById(@RequestBody BoardIdCarrier idCarrier){

        JsonObject jo = new JsonObject();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        jo.addProperty("destination", pageService.crrBoardPagePosition(idCarrier.getId()));

        return new ResponseEntity<>(jo.toString(), header, HttpStatus.OK);
    }
    //단순 신규 게시물 생성 GET
    @GetMapping(path = "/new")
    public String newBoardGet(Model model, @AuthenticationPrincipal User user){

        model.addAttribute("CurrUsername", user.getUsername());

        return "html/adminManage/board/manageBoardNew";
    }
    //단순 신규 게시물 생성 POST
    @PostMapping(path = "/new")
    @ResponseBody
    public ResponseEntity<?> newBoardPost(BoardDto req){
        HttpHeaders h = new HttpHeaders();
        long dest;

        try{
            dest = boardService.newBoard(req);
        }
        catch (Exception e){
            h.setLocation(URI.create("/adminManage/board"));
            return new ResponseEntity<>(h, HttpStatus.BAD_REQUEST);
        }

        //req를 이용하여 새로운 게시글 생성 서비스 이용, 리턴값은 새로 생성된 게시글의 id

        //새로 만들어진 게시글의 id를 이용하여 URI 생성하여 게시글 내용으로 이동
        h.setLocation(URI.create("/adminManage/board/view/"+dest));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }


    //수정 페이지 GET, try-catch로 에러처리 시도
    @GetMapping(path = "/modifying/{id}")
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
        return "html/adminManage/board/manageBoardModifying";
    }
    //기존 수정 페이지 POST
    @PostMapping(path ="/modifying/{id}")
    @ResponseBody
    public ResponseEntity<?> modifyingBoardPost(@PathVariable("id") long id, BoardDto req, @AuthenticationPrincipal User user){
        HttpHeaders h = new HttpHeaders();

        req.setBoard_id(id);
        if(!boardService.modifyBoard(req, user.getUsername(), user.getAuthorities())){
            h.setLocation(URI.create("error/403"));
            return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        }

        h.setLocation(URI.create("/adminManage/board/view/"+id));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        //변경된 게시물의 게시글로 자동 이동
    }


    //게시글 삭제
    @GetMapping(path="/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteBoard(@PathVariable("id") long id, @AuthenticationPrincipal User user){
        HttpHeaders h = new HttpHeaders();

        if(!boardService.deleteBoard(id, user.getUsername(), user.getAuthorities())){
            h.setLocation(URI.create("error/403"));
            return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
        }

        h.setLocation(URI.create("/adminManage/board/1"));
        return new ResponseEntity<>(h, HttpStatus.MOVED_PERMANENTLY);
    }

    //게시글 숨김, 숨김해제
    @PostMapping(path="/modifyHide")
    @ResponseBody
    public ResponseEntity<?> adminHideModify(@RequestBody HideRequestDto modifyJSON, @AuthenticationPrincipal User user){

        boolean serviceResponse = boardService.updateHide(modifyJSON, user.getAuthorities());


        if (serviceResponse) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
