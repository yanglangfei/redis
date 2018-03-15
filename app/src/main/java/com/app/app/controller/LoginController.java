package com.app.app.controller;
import com.app.app.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public  String  login(Long userId){
        String loginInfo="用户:"+userId+"登录成功";
        tokenService.saveUserToken(userId,loginInfo);
        return "login success";
    }

}
