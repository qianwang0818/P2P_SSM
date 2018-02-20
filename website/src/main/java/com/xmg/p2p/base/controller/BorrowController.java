package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 借款申请相关的控制层
 * @Author: Squalo
 * @Date: 2018/2/20 - 22:01     day05_05
 */
@Controller
@Slf4j
public class BorrowController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserinfoService userinfoService;

    /**顶部菜单栏'我要借款'按钮触发,跳转到借款中心*/
    @RequestMapping("borrow")
    public String borrowCenter(Model model){
        Logininfo current = UserContext.getCurrent();
        if(current==null){      //未登录,返回一个静态html页面
            return "redirect:borrow.html";
        }else{                  //已登录,返回FreeMarker的ftl页面
            model.addAttribute("account",accountService.getCurrent());
            model.addAttribute("userinfo",userinfoService.getCurrent());
            model.addAttribute("baseAuthScore", BidConst.BASE_AUTH_SCORE);
            return "borrow";
        }
    }
}
