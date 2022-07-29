package com.mszl.blog.controller;

import com.mszl.blog.service.LoginService;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ReturnObject register(@RequestBody LoginParams loginParams){
        return loginService.register(loginParams);
    }
}
