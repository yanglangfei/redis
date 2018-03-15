package com.app.app.service;

public interface TokenService {


    void  saveUserToken(Long userId, String loginInfo);


    String   getUserToken(Long userId);

    void  cleanToken(Long userId);

}
