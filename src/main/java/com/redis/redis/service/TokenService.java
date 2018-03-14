package com.redis.redis.service;

public interface TokenService {


    void  saveUserToken(Long userId,String loginInfo);


    String   getUserToken(Long userId);

    void  cleanToken(Long userId);

}
