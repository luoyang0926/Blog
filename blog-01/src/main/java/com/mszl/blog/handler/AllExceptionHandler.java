package com.mszl.blog.handler;

import com.mszl.blog.vo.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//对加了@Controller注解的方法进行拦截处理
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnObject doException(Exception ex) {
        ex.printStackTrace();
        return ReturnObject.fail(-999, "系统异常");
    }

}
