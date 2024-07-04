package com.practice.Skilltest.adminManage.service.Impi;

import com.practice.Skilltest.adminManage.dao.AdminManageBoardDao;
import com.practice.Skilltest.adminManage.service.AdminBoardService;
import com.practice.Skilltest.board.dao.BoardDao;
import com.practice.Skilltest.board.dto.BoardDto;
import com.practice.Skilltest.board.dto.HideRequestDto;
import com.practice.Skilltest.board.service.BoardService;
import com.practice.Skilltest.user.role.UserRoles;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@Qualifier("AdminBoardService")
public class AdminBoardServiceImpl implements AdminBoardService {


    //private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AdminManageBoardDao boardDao;

    public AdminBoardServiceImpl(AdminManageBoardDao adminManageBoardDao) {
        this.boardDao = adminManageBoardDao;
    }

    //2024.02.26 게시글 접근 시 해당하는 id의 게시글이 있는지 조회
    @Override
    public boolean checkById(long id) {
        return boardDao.checkById(id)==1;
    }

    //게시글 내용 조회
    @Override
    public BoardDto viewOne(long id) throws Exception {

        BoardDto result = boardDao.viewOne(id);

        //log.info(result.toString());
        if(!checkById(id)){throw new NotFoundException("존재하지 않는 게시물 id 접근");}
        else{
            upView(id);

            //timestamp형 string 변환
            result.setCreated_time_date(result.getCreated_time().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            return result;
        }
    }

    //새 게시글l
    public long newBoard(BoardDto req) throws IllegalArgumentException {

        switch(checkValidBoard(req)){
            case 1: break;
            case 2: throw new IllegalArgumentException("there are no data in title.");
            case 3: throw new IllegalArgumentException("There are no data in writer.");
            case 4: throw new IllegalArgumentException("There are no data in content.");
            case 5: throw new IllegalArgumentException("Data length of content is exceeded limit.");
        }

        boardDao.newBoard(req);

        return req.getBoard_id();
    }

    //게시글 수정 작업
    @Override
    public boolean modifyBoard(BoardDto req, String username, Collection<? extends GrantedAuthority> userAuthority) {

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
    public boolean deleteBoard(long id, String username, Collection<? extends GrantedAuthority> userAuthority) {

        //요청자 유효 확인
        if(!checkValidRequester(id,username,userAuthority)) {return false;}

        boardDao.deleteBoard(id);
        return true;
    }

    //요청자의 세션 정보가 해당 게시글의 작성자와 동일자 요청//어드민의 요청인지 확인
    @Override
    public boolean checkValidRequester(long id, String username, Collection<? extends GrantedAuthority> userAuthority) {


        //요청이 해당 게시글의 게시자와 동일한 유저가 요청하였는지 확인
        if(boardDao.getWriter(id).equals(username)){ return true; }


        //만약 아니라면 해당 요청이 어드민 요청인지 확인
        return userAuthority.contains(new SimpleGrantedAuthority(UserRoles.ADMIN.getValue()));
    }

    //입력받은 board데이터의 데이터 입력 여부 및 데이터 크기 검증
    //현재 2000자 길이 글이 rich editor를 사용할 경우 최대 8000자까지 사용 될 것으로 게산하여 구현
    @Override
    public int checkValidBoard(BoardDto boardDto){

        /* 문제있는 부분이 있을 시 각 부분별로 값 리턴, 없으면 1 리턴
         * 비어있을 때 전달되는 값 : 제목(2), 작성자(3), 콘텐츠(4)
         * 허용된 길이가 넘을 때 : 콘텐츠(5) */

        String t_data = boardDto.getTitle().trim();
        String w_data = boardDto.getWriter().trim();
        String c_data = boardDto.getContent().trim();
        //빈칸만 입력되어있는 경우 넘어가는 것 방지

        if(t_data.isEmpty()){
            return 2;
        }else if (w_data.isEmpty()){
            return 3;
        }else if (c_data.isEmpty()){
            return 4;
        }

        if(boardDto.getContent().length()>8000){
            return 5;
        }

        return 1;
    }
    //게시글 숨김/숨김해제 처리
    @Override
    public boolean updateHide(HideRequestDto hideRequestDto, Collection<? extends GrantedAuthority> userAuthority) {

        if (!userAuthority.contains(new SimpleGrantedAuthority(UserRoles.ADMIN.getValue()))) {
            return false;
        }
        return boardDao.updateHide(hideRequestDto);
    }
}
