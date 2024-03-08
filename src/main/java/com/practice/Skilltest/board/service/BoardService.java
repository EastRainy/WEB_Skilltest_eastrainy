package com.practice.Skilltest.board.service;

import com.practice.Skilltest.board.dto.BoardDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public interface BoardService {

    boolean checkById(long id);

    BoardDto viewOne(long id) throws Exception;

    long newBoard(BoardDto req);

    boolean modifyBoard(BoardDto req);

    void upView(long id);

    void deleteBoard(long id);

    boolean checkValidModify(long id, String username, Collection<? extends GrantedAuthority> userAuthority);

}
