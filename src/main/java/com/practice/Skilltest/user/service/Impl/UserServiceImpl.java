package com.practice.Skilltest.user.service.Impl;

import com.practice.Skilltest.user.dao.UserDao;
import com.practice.Skilltest.user.dao.UserLoginDao;
import com.practice.Skilltest.user.dto.PasswordDto;
import com.practice.Skilltest.user.dto.UserDetailDto;
import com.practice.Skilltest.user.dto.UserDto;
import com.practice.Skilltest.user.role.UserRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class UserServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserLoginDao userLoginDao;
    private final UserDao userDao;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserLoginDao userLoginDao, UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userLoginDao = userLoginDao;
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //유저 이름을 받아 db에서 정보를 가져와서 UserDetails 설정
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(checkByUsername(username)) {throw new UsernameNotFoundException("아이디가 없습니다.");}
        //아이디가 없으면 예외

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        if(username.equals("admin")){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(UserRoles.ADMIN.getValue()));
        }else {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(UserRoles.MEMBER.getValue()));
        }
        //테스트용으로 admin 이 아이디일경우 어드민 롤 부여


        return User.builder()
                .username(userLoginDao.refer_id(username))
                .password(userLoginDao.refer_pw(username))
                .authorities(grantedAuthoritySet)
                .build();
        //유저 빌드해서 전달
    }
    // * 이후 스프링 시큐리티 로직에서 비밀번호 비교하는 서비스 존재 //

    //회원가입
    public void signupUser(UserDto userDto) throws Exception{

        Map<String, Object> params;

        //입력된 속성중에 비어있는 공간이 있을 경우 예외처리
        if(userDto.checkIsEmpty()){throw new Exception("요청 데이터 중 입력되지 않은 값이 있습니다.");}

        //아이디, 비밀번호 확인
        if(!userDto.getPassword().equals(userDto.getPassword_check())){
            throw new Exception("비밀번호와 비밀번호 확인이 다릅니다.");
        }
        else if(!checkByUsername(userDto.getUsername())){
            throw new Exception("중복되는 아이디입니다.");
        }
        //아이디, 비밀번호 외 입력데이터 검증

        params = userDto.toMap();
        //암호화된 패스워드
        params.put("password",bCryptPasswordEncoder.encode(userDto.getPassword()));
        //LocalDate로 변환된 bitrhdate
        LocalDate ld = LocalDate.parse(userDto.getBirthdate());
        params.put("birthdate",ld);

        userLoginDao.signup_user(params);

    }

    //로그인 성공시 해당 username의 마지막 로그인 시간 갱신
    public void UpdatingLastLoginTime(String username){
        userLoginDao.lastlogin_update(username);
    }
    //username에 해당하는 아이디가 있는 지 조회하여 없는경우 true 리턴
    public boolean checkByUsername(String username){
        return userLoginDao.refer_id(username)==null;
    }


    //사용자에게 전달하기 위해 데이터베이스에서 유저의 정보를 가져옴
    public UserDetailDto getUserDetails(User user){
        UserDetailDto dto = userDao.getUserData(user.getUsername());
        dto.setBirthdate_string(dto.getBirthdate().toString());

        return dto;
    }

    //사용자가 입력한 데이터로 업데이트
    public boolean updateUserData(UserDetailDto userData, String username){
        userData.setUsername(username);
        userData.setBirthdate(LocalDate.parse(userData.getBirthdate_string()));
        return userDao.updateUserData(userData.toMap());
    }

    //사용자가 입력한 비밀번호로 비밀번호 변경
    public Map<String, Object> updateUserPassword(User user, PasswordDto passwordDto, BindingResult bindingResult){

        Map<String, Object> outputMap = new HashMap<>();
        Map<String, Object> inputMap = new HashMap<>();
        //유효성 최종 검사
        //DTO 로 각 조건 확인 후 비어 있는지, 같은 지 여부 최종 검사

        outputMap.put("updated", false);

        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            outputMap.put("message", errorList.get(0).getDefaultMessage());
            return outputMap;
        }

        if(passwordDto.getPassword().isEmpty() || passwordDto.getPassword_check().isEmpty()){
            if(passwordDto.getPassword().isEmpty()){
                outputMap.put("message", "비밀번호를 입력해주세요.");

            }else{
                outputMap.put("message", "비밀번호 확인을 입력해주세요.");
            }
            return outputMap;
        }
        if(!passwordDto.getPassword().equals(passwordDto.getPassword_check())){
            outputMap.put("message","비밀번호와 비밀번호 확인이 동일하지 않습니다.");
            return outputMap;
        }

        //DB에 전송하여 비밀번호 변경


        inputMap.put("username",user.getUsername());
        inputMap.put("password", bCryptPasswordEncoder.encode(passwordDto.getPassword()));
        userDao.updatePassword(inputMap);
        
        //결과 컨트롤러에 전송
        outputMap.replace("updated", true);
        return outputMap;
    }


}
