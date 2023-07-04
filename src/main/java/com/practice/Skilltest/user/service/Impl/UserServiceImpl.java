package com.practice.Skilltest.user.service.Impl;

import com.practice.Skilltest.user.dao.UserLoginDao;
import com.practice.Skilltest.user.dto.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserLoginDao userLoginDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //유저 이름을 받아 db에서 정보를 가져와서 UserDetails 설정
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(checkByUsername(username)) {throw new UsernameNotFoundException("아이디가 없습니다.");}
        //아이디가 없으면 예외

        String role;
        if(username.equals("testadmin")){
            role = "ADMIN";
        }else {
            role = "USER";
        }
        //테스트용으로 testadmin 이 아이디일경우 어드민 롤 부여

        return User.builder()
                .username(userLoginDao.refer_id(username))
                .password(userLoginDao.refer_pw(username))
                .roles(role)
                .build();
    }
    // * 이후 스프링 시큐리티 로직에서 비밀번호 비교하는 서비스 존재 //



    //회원가입
    public int signupUser(UserDto userDto){

        String username = userDto.getId();
        String encPassword = bCryptPasswordEncoder.encode(userDto.getPassword());

        if(userLoginDao.refer_id(username)!=null){
            return 0;
        }

        UserDto user_encoded = new UserDto(username, encPassword);

        userLoginDao.signup_user(user_encoded);

        return 1;
    }

    public boolean checkByUsername(String username){
        return userLoginDao.refer_id(username)==null;
    }
}
