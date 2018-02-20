package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.util.BidConst;
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

    /**跳转到个人中心*/
    @RequireLogin
    @RequestMapping("personal")
    public String personalCenter(Model model){
        Logininfo current = UserContext.getCurrent();       //从Session中取出当前登录的Logininfo
        Long id = current.getId();
        model.addAttribute("userinfo",userinfoService.get(id));
        model.addAttribute("account",accountService.get(id));
        return "personal";
    }

    /**手机认证模态框的保存按钮,提交手机号和验证码过来做验证*/
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

    /**点击认证邮件的链接,完成邮箱绑定*/
    @RequestMapping("bindEmail")
    public String bindEmail(String key, Model model){
        try {
            userinfoService.bindEmail(key);
            model.addAttribute("success",true);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            model.addAttribute("success",false);
            model.addAttribute("msg",e.getMessage());
        }
        return "checkmail_result";
    }

}
