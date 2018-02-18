package com.xmg.mgrsite.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登录控制层
 * @Author: Squalo
 * @Date: 2018/2/18 - 13:06     day04_02
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private ILogininfoService logininfoService;


    /**后台登录方法*/
    @RequestMapping("login")
    @ResponseBody
    public JSONResult login (String username, String password, HttpServletRequest request){
        log.info("【LoginController】收到用户登陆请求,用户名:{},密码:{}",username,password);
        String ip = request.getRemoteAddr();
        Logininfo logininfo = logininfoService.login(username, password, ip, Logininfo.USER_MANAGER);
        JSONResult jsonResult = new JSONResult();
        if(logininfo==null){    //登录失败
            jsonResult.setSuccess(false);
            jsonResult.setMsg("用户名或密码有误!");
        }
        return jsonResult;
    }

    /**登录成功,访问后台首页*/
    @RequestMapping("index")
    public String index(){
        return "main";
    }
}
