package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 手机验证码/邮箱认证相关的控制层
 * @Author: Squalo
 * @Date: 2018/2/18 - 17:52     day04_06
 */
@Controller
@Slf4j
public class VerifyController {

    @Autowired
    private IVerifyService verifyService;

    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber){
        log.info("【VerifyCodeController:sendVerifyCode】phoneNumber:{}",phoneNumber);
        JSONResult jsonResult = new JSONResult();
        try {
            verifyService.sendVerifyCode(phoneNumber);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

    /**邮箱认证,发送邮箱认证邮件*/
    @RequireLogin
    @RequestMapping("sendEmail")
    @ResponseBody
    public JSONResult sendEmail(String email){
        JSONResult jsonResult = new JSONResult("认证邮件已发送,请打开邮件点击完成认证,有效期为"+ BidConst.VERIFYEMAIL_VALIDATE_DAY +"天.");
        try {
            //调用IVerifyService方法,执行发送邮件和保存VO到Redis业务
            verifyService.sendVerifyEmail(email);
        } catch (RuntimeException e) {
            log.error("【PersonalController:sendEmail】{}",e.getMessage());
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }

}
