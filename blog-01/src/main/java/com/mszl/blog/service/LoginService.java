package com.mszl.blog.service;

import com.mszl.blog.pojo.SysUser;
import com.mszl.blog.vo.ReturnObject;
import com.mszl.blog.vo.params.LoginParams;
import org.springframework.transaction.annotation.Transactional;

import javax.jnlp.BasicService;

public interface LoginService {
    //登录
    Object login(LoginParams loginParams);

    SysUser checkToken(String token);

    ReturnObject logout(String token);

    ReturnObject register(LoginParams loginParams);
}
