package com.xmg.mgrsite.base.controller;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实名认证审核相关
 * @Author: Squalo
 * @Date: 2018/2/23 - 21:54     day06_07
 */
@Controller
@Slf4j
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;

    /**分页条件查询实名认证对象列表*/
    @RequestMapping("realAuth")
    public String realAuth( @ModelAttribute("qo") RealAuthQueryObject qo, Model model){
        model.addAttribute("pageResult",realAuthService.query(qo));
        return "realAuth/list";
    }

    /**实名认证的审核*/
    @RequestMapping("realAuth_audit")
    @ResponseBody
    public JSONResult realAuthAudit(RealAuth form){
        JSONResult jsonResult = new JSONResult("审核成功!");
        try {
            realAuthService.audit(form);    //实际上只有三个参数: id, remark, state
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}
