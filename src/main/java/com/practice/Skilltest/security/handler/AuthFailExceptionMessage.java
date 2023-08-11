package com.practice.Skilltest.security.handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public enum AuthFailExceptionMessage {
    BadCredential(BadCredentialsException.class,"아이디 혹은 비밀번호가 잘못되었습니다."),
    UsernameNotFoundException(UsernameNotFoundException.class,"아이디 혹은 비밀번호가 잘못되었습니다."),
    LockedException(LockedException.class,"잠긴 계정입니다."),
    DisabledException(DisabledException.class,"비활성화된 계정입니다."),
    AccountExpiredException(AccountExpiredException.class,"만료된 계정입니다."),
    CredentialsExpiredException(CredentialsExpiredException.class,"비밀번호가 만료된 계정입니다."),
    AuthenticationServiceException(AuthenticationServiceException.class,"인증 오류입니다. 잠시 후 시도해주세요.");
    private final Class<? extends AuthenticationException> exceptionClass;
    private final String message;
    AuthFailExceptionMessage(Class<? extends AuthenticationException> exceptionClass, String message){
        this.exceptionClass = exceptionClass;
        this.message = message;
    }
    //Exception 종류에 따라 열거형 생성

    //입력받은 Exception e와 exceptionMessage의 클래스 비교하여 인스턴스가 같으면 메세지 리턴
    public static String getMessage(AuthenticationException e){
        for(AuthFailExceptionMessage exceptionMessage : values()){
            if(exceptionMessage.exceptionClass.isInstance(e)){
                return exceptionMessage.message;
            }
        }
        return "인증 오류입니다.";
    }
}



