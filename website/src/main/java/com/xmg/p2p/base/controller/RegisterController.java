package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于注册/登陆相关
 * @Author: Squalo
 * @Date: 2018/2/15 - 23:26
 */
@Controller
@Slf4j
public class RegisterController {

    @Autowired
    private ILogininfoService logininfoService;

    @RequestMapping("register")
    @ResponseBody
    public JSONResult register(String username, String password){
        log.info("【RegisterController】收到用户注册请求,用户名:{},密码:{}",username,password);
        JSONResult jsonResult = new JSONResult();
        try {
            logininfoService.register(username,password);
        } catch (RuntimeException e) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**异步校验用户名是否已存在,已存在返回false表示校验失败*/
    @RequestMapping("checkUsername")
    @ResponseBody
    public Boolean checkUserName(String username){
        return !logininfoService.checkUsername(username);
    }

    /**用户名登陆,成功返回true*/
    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username, String password){
        log.info("【RegisterController】收到用户登陆请求,用户名:{},密码:{}",username,password);
        JSONResult jsonResult = new JSONResult();
        try {
            logininfoService.login(username,password);
        } catch (RuntimeException e) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return jsonResult;
    }


}
