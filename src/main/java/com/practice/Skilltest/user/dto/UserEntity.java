package com.practice.Skilltest.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class UserEntity {

    private String username;
    private String password;

    public UserEntity(){
        this.username = null;
        this.password = null;
    }

    public UserEntity(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
