package com.mszl.blog.controller;

import com.mszl.blog.service.LoginService;
import com.mszl.blog.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public ReturnObject logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
