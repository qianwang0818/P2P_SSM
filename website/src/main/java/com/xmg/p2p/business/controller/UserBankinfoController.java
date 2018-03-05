package com.xmg.p2p.business.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.business.domain.UserBankInfo;
import com.xmg.p2p.business.service.IUserBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户绑定银行卡
 * @Author: Squalo
 * @Date: 2018/3/5 - 20:59      day11_01
 */
@Controller
public class UserBankinfoController {

    @Autowired
    private IUserBankService userBankService;

    @Autowired
    private IUserinfoService userinfoService;

    /**跳转到绑定银行卡页面*/
    @RequireLogin
    @RequestMapping("/bankInfo")
    public String bankInfo(Model model){
        //判断用户是否已绑卡
        Userinfo userinfo = userinfoService.getCurrent();
        if(userinfo.isBindBank()){  //已经绑定银行卡
            UserBankInfo bankInfo = userBankService.selectByUser(userinfo.getId());
            model.addAttribute("bankInfo",bankInfo);
            return "bankInfo_result";
        }else{                      //没有绑定银行卡
            model.addAttribute("userinfo",userinfo);
            return "bankInfo";
        }
    }

    /**保存银行卡*/
    @RequestMapping("/bankInfo_save")
    public String bankinfoSave(UserBankInfo form){

        return null;
    }
}
