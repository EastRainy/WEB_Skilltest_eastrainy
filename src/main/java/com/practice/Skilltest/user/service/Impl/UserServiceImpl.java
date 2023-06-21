package com.practice.Skilltest.user.service.Impl;

import com.practice.Skilltest.user.dao.UserLoginDao;
import com.practice.Skilltest.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserLoginDao userLoginDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        return null;
    }

    public int signupUser(User user){

        String username = user.getName();
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());

        if(userLoginDao.refer_id(username)!=null){
            return 0;
        }

        UserDto userDto = new UserDto(username, encPassword);

        userLoginDao.signup_user(userDto);

        return 1;
    }

    public boolean checkByUsername(String username){
        return userLoginDao.refer_id(username)==null;
    }




}
