package com.practice.Skilltest.board.service.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.PageService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PageServiceImpl implements PageService {

    long totalPage = 0;
    private final BoardDao boardDao;
    public PageServiceImpl(BoardDao boardDao){
        this.boardDao = boardDao;
        calTotalPage();
    }

    //총 페이지 수 계산
    @Override
    public void calTotalPage() {
        totalPage = (boardDao.selectCount() -1)/10 + 1;
    }

    //현재 입력된 페이지가 유효한 범위의 페이지인지 검증
    @Override
    public boolean checkValid(long crrPage) {
        calTotalPage();
        return crrPage > 0 && crrPage <= totalPage;
    }

    //현재 페이지 블록의 시작, 끝페이지 범위 전달
    public long[] pageRange(long crrPage){
        calTotalPage();
        long[] pagerange = new long[2];
        pagerange[0] = (((crrPage-1)/10) * 10) + 1;
        pagerange[1] = Math.min(pagerange[0] + 9, totalPage);

        return pagerange;
    }
    
    //next(다음 10개)가 있는지 판별
    @Override
    public boolean haveNext(long crrPage) {
        return (crrPage-1) / 10 < totalPage / 10;
    }
    
    
    //현재 페이지에 해당하는 boardDto 리스트
    @Override
    public List<BoardDto> selectedPageList(long crrPage) {


        return boardDao.selectPageRange((10 *(crrPage-1)));
    }

    
    //요청한 게시글의 페이지 위치
    public long crrBoardPagePosition(long id){

        //들어온 id가 올바른 id 인지 검증, 아닐 시 첫번째 페이지로
        if(boardDao.checkById(id)==0){return 1;}

        //입력된 id 보다 최신인 글의 수를 확인하여 해당 게시글의 페이지 위치 계산
        //나보다 id가 큰 게시물이 9개 있으면 => 10번째 게시물 9/10 + 1 = 1페이지
        //나보다 id가 큰 게시물이 10개 있으면 => 11번째 게시물 10/10 +1 = 2페이지
        long idPosition = (boardDao.selectNewer(id))/10 + 1;


        //이동할 페이지 번호 리턴, 만약의 경우를 위한 검증로직
        if(checkValid(idPosition)){ return idPosition; }
        else{
            return 1;
        }
    }

}