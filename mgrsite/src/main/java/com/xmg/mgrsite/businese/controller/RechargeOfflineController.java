package com.xmg.mgrsite.businese.controller;

import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台的线下充值控制层
 * @Author: Squalo
 * @Date: 2018/3/2 - 14:34      day09_04
 */
@Controller
@Slf4j
public class RechargeOfflineController {

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    @Autowired
    private IPlatformBankInfoService bankInfoService;

    /**进入线下充值审核列表页面*/
    @RequestMapping("rechargeOffline")
    public String rechargeOffline(@ModelAttribute("qo")RechargeOfflineQueryObject qo, Model model){
        model.addAttribute("pageResult",rechargeOfflineService.query(qo));
        model.addAttribute("banks",bankInfoService.selectAll());
        return "rechargeoffline/list";
    }

    /**线下充值审核*/
    @RequestMapping("rechargeOffline_audit")
    @ResponseBody
    public JSONResult audit(RechargeOffline form){
        JSONResult jsonResult = new JSONResult("审核成功!");
        try {
            rechargeOfflineService.audit(form);    //只有 id, remark, state
        } catch (RuntimeException e) {
            log.error("【RechargeOfflineController:audit】发送异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

}
