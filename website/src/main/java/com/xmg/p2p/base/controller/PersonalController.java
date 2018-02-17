package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 个人中心
 * @Author: Squalo
 * @Date: 2018/2/17 - 12:59     day03_08
 */
@Controller
public class PersonalController {

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IAccountService accountService;

    @RequestMapping("personal")
    public String personalCenter(Model model){
        Logininfo current = UserContext.getCurrent();       //从Session中取出当前登录的Logininfo
        Long id = current.getId();
        model.addAttribute("userinfo",userinfoService.get(id));
        model.addAttribute("account",accountService.get(id));
        return "personal";
    }
}
