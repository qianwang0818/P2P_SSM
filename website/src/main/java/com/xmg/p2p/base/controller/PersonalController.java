package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人中心
 * @Author: Squalo
 * @Date: 2018/2/17 - 12:59     day03_08
 */
@Controller
@Slf4j
public class PersonalController {

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IAccountService accountService;

    @RequireLogin
    @RequestMapping("personal")
    public String personalCenter(Model model){
        Logininfo current = UserContext.getCurrent();       //从Session中取出当前登录的Logininfo
        Long id = current.getId();
        model.addAttribute("userinfo",userinfoService.get(id));
        model.addAttribute("account",accountService.get(id));
        return "personal";
    }

    @RequireLogin
    @RequestMapping("bindPhone")
    @ResponseBody
    public JSONResult bindPhone(VerifyCodeVO verifyCodeVO){
        JSONResult jsonResult = new JSONResult();
        try {   //拿着传入的手机和验证码,比对Redis中的数据
            userinfoService.bindPhone(verifyCodeVO);    //在UserinfoService中做Redis校验
        } catch (RuntimeException e) {
            log.error("【PersonalController:bindPhone】{}",e.getMessage());
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }
}
