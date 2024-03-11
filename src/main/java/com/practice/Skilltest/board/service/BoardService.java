package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public interface BoardService {

    //해당 id의 게시물이 존재하는지 조회
    boolean checkById(long id);

    //해당 id의 게시물의 상세사항 조회
    BoardDto viewOne(long id) throws Exception;

    //새로운 게시물 생성
    long newBoard(BoardDto req);

    //게시물 수정
    boolean modifyBoard(BoardDto req, String username, Collection<? extends GrantedAuthority> userAuthority);

    //게시물 조회시 조회수 증가
    void upView(long id);

    //게시글 삭제
    boolean deleteBoard(long id, String username, Collection<? extends GrantedAuthority> userAuthority);

    //게시글 수정, 삭제시 유효 요청자인지 검증
    boolean checkValidModify(long id, String username, Collection<? extends GrantedAuthority> userAuthority);

}
