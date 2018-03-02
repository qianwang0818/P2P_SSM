package com.xmg.p2p.business.controller;

import com.fasterxml.jackson.core.json.JsonReadContext;
import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.UserContext;
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
 * 前台的线下充值控制层
 * @Author: Squalo
 * @Date: 2018/3/2 - 13:02      day09_03
 */
@Controller
@Slf4j
public class RechargeOfflineController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    /**进入线下充值页面*/
    @RequireLogin
    @RequestMapping("recharge")
    public String recharge(Model model){
        model.addAttribute("banks",platformBankInfoService.selectAll());
        return "recharge";
    }

    /**提交线下充值单*/
    @RequireLogin
    @RequestMapping("recharge_save")
    @ResponseBody
    public JSONResult rechargeSave(RechargeOffline rechargeOffline){
        JSONResult jsonResult = new JSONResult("线下充值记录已提交，等待平台人员审核!");
        try {
            rechargeOfflineService.apply(rechargeOffline);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    /**进入线下充值记录页面*/
    @RequireLogin
    @RequestMapping("recharge_list")
    public String rechargeList(@ModelAttribute("qo")RechargeOfflineQueryObject qo, Model model){
        qo.setApplierId(UserContext.getCurrent().getId());
        model.addAttribute("pageResult",rechargeOfflineService.query(qo));
        return "recharge_list";
    }

}
