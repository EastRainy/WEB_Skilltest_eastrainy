package com.practice.Skilltest.board.service.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.BoardService;
import com.practice.Skilltest.user.role.UserRoles;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardServiceImpl implements BoardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BoardDao boardDao;

    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }


    //2024.02.26 게시글 접근 시 해당하는 id의 게시글이 있는지 조회
    @Override
    public boolean checkById(long id) {
        return boardDao.checkById(id)==1;
    }

    //게시글 내용 조회
    @Override
    public BoardDto viewOne(long id) throws Exception {

        if(!checkById(id)){throw new NotFoundException("존재하지 않는 게시물 id 접근");}

        else{
            upView(id);
            return boardDao.viewOne(id);
        }
    }

    //새 게시글
    public long newBoard(BoardDto req) {

        boardDao.newBoard(req);

        return req.getBoard_id();
    }

    //게시글 수정 작
    @Override
    public boolean modifyBoard(BoardDto req) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", req.getBoard_id());
        map.put("title",req.getTitle());
        map.put("content", req.getContent());

        return boardDao.updateBoard(map);
    }

    //조회수 증가
    @Override
    public void upView(long id) {
        boardDao.updateViewcount(id);
    }

    //게시글 삭제
    @Override
    public void deleteBoard(long id, String username, Collection<? extends GrantedAuthority> userAuthority) {

        if(!checkValidModify(id,username,userAuthority)) { return; }

        boardDao.deleteBoard(id);
    }



    //요청자의 세션 정보가 해당 게시글의 작성자와 동일자 요청//어드민의 요청인지 확인
    @Override
    public boolean checkValidModify(long id, String username, Collection<? extends GrantedAuthority> userAuthority) {

        //요청이 해당 게시글의 게시자와 동일한 유저가 요청하였는지 확인
        if(boardDao.getWriter(id).equals(username)){ return true; }

        logger.info("삭제자가 게시자와 다른 요청");
        //만약 아니라면 해당 요청이 어드민 요청인지 확인
        if(userAuthority.contains(new SimpleGrantedAuthority(UserRoles.ADMIN.getValue()))) {
            
            logger.info("어드민 게시글 삭제요청");
            return true;
        }

        logger.info("삭제자가 게시자와 다르고 어드민이 아닌 요청");
        return false;
    }

}

