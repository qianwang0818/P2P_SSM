package com.xmg.p2p.base.interceptor;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于登录检查的拦截器.
 * 可拦截未登录用户访问需要登录才能访问的方法.
 * 如果未登录用户访问有@RequireLogin注解的方法,会跳转到登录页面.
 * @Author: Squalo
 * @Date: 2018/2/18 - 15:40     day04_05
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断登录逻辑
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            RequireLogin rl = hm.getMethodAnnotation(RequireLogin.class);
            if(rl!=null && UserContext.getCurrent()==null){         //如果 所访问的方法有打@RequireLogin注解 并且 当前用户未登录
                response.sendRedirect("/login.html");     //跳转到登录页面
                return false;                                       //阻止执行拦截器链上的后续拦截器
            }
        }
        //如果没有被拦住,放行
        return true;
    }
}
