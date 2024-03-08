package com.practice.Skilltest.user.service.Impl;

import com.practice.Skilltest.user.dao.UserLoginDao;
import com.practice.Skilltest.user.dto.UserEntity;
import com.practice.Skilltest.user.dto.UserSignupEntity;
import com.practice.Skilltest.user.role.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


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
    public void signupUser(UserSignupEntity userEntity) throws Exception{

        if(!userEntity.getPassword().equals(userEntity.getPassword_check())){
            throw new Exception("비밀번호와 비밀번호 확인이 다릅니다.");
        }

        if(checkByUsername(userEntity.getUsername())) {
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
            userLoginDao.signup_user(userEntity);
        }
        else{
            throw new Exception("중복되는 아이디입니다.");
        }
    }

    //로그인 성공시 해당 username의 마지막 로그인 시간 갱신
    public void UpdatingLastLoginTime(String username){
        userLoginDao.lastlogin_update(username);
    }

    //username에 해당하는 아이디가 있는 지 조회하여 없는경우 true 리턴
    private boolean checkByUsername(String username){
        return userLoginDao.refer_id(username)==null;
    }
}
