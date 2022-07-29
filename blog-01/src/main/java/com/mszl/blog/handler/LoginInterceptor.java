package com.mszl.blog.handler;

import com.alibaba.fastjson.JSON;
import com.mszl.blog.pojo.SysUser;
import com.mszl.blog.service.LoginService;
import com.mszl.blog.utils.UserThreadLocal;
import com.mszl.blog.vo.ErrorCode;
import com.mszl.blog.vo.ReturnObject;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1. 需要判断,请求接口路径 是否为HandleMethod(Controller)
         * 2. 判断 token是否为空，如果为空 未登录
         * 3. 如果token不为空，登录验证loginService checkToken
         * 4.认证成功，放行
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (token == null){
            ReturnObject returnObject = ReturnObject.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(returnObject));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            ReturnObject result = ReturnObject.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //是登录状态，放行
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal 中用完的信息 会存在内存泄露的风险
        UserThreadLocal.remove();
    }
}
