package com.mszl.blog.controller;

import com.mszl.blog.pojo.SysUser;
import com.mszl.blog.utils.UserThreadLocal;
import com.mszl.blog.vo.ReturnObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Object test(){
        SysUser user = UserThreadLocal.get();
        System.out.println(user);
        return ReturnObject.success(null);
    }
}
