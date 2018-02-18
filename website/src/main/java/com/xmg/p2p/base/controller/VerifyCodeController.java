package com.xmg.p2p.base.controller;

import com.alibaba.druid.support.json.JSONParser;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 验证码相关的控制层
 * @Author: Squalo
 * @Date: 2018/2/18 - 17:52     day04_06
 */
@Controller
@Slf4j
public class VerifyCodeController {

    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber){
        log.info("【VerifyCodeController:sendVerifyCode】phoneNumber:{}",phoneNumber);
        JSONResult jsonResult = new JSONResult();
        return jsonResult;
    }
}
