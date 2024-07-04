package com.practice.Skilltest.adminManage.service.Impi;

import com.practice.Skilltest.adminManage.dao.AdminManageBoardDao;
import com.practice.Skilltest.adminManage.service.AdminPageService;
import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.service.impl.PageServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Qualifier("AdminPageService")
public class AdminPageServiceImpl extends PageServiceImpl implements AdminPageService {

    final BoardDao boardDao;
    public AdminPageServiceImpl(AdminManageBoardDao adminManageBoardDao) {
        super(adminManageBoardDao);
        this.boardDao = adminManageBoardDao;
    }

}
