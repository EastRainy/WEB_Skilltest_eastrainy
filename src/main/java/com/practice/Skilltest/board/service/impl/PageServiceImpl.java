package com.practice.Skilltest.board.service.impl;

import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.service.PageService;
import org.springframework.stereotype.Repository;

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
    //현재 페이지의 시작, 끝페이지 전달
    public long[] pageRange(long crrPage){
        calTotalPage();
        long[] pagerange = new long[2];
        pagerange[0] = (((crrPage-1)/10) * 10) + 1;
        pagerange[1] = Math.min(pagerange[0] + 9, totalPage);

        return pagerange;
    }
    @Override
    public boolean haveNext(long crrPage) {
        return (crrPage-1) / 10 < totalPage / 10;
    }
    //현재 페이지에 해당하는 boardDto 리스트
    @Override
    public List<BoardDto> selectedPageList(long crrPage) {
        return boardDao.selectPageRange((10 *(crrPage-1)));
    }
}
