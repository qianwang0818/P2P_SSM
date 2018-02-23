package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实名认证控制
 * @Author: Squalo
 * @Date: 2018/2/23 - 12:08     day06_03
 */
@Controller
@Slf4j
public class RealAuthController {

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IRealAuthService realAuthService;

    @RequireLogin
    @RequestMapping("realAuth")
    public String realAuth(Model model){
        //得到当前userinfo
        Userinfo current = userinfoService.getCurrent();
        //对三种认证情况进行判断
        if(!current.isRealAuth()){  //如果用户没有实名认证.
            if(current.getRealAuthId()==null){   //(1)如果userinfo上没有realAuthId,即未认证,跳到realAuth.ftl.
                return "realAuth";
            }else{                               //(2)如果userinfo上有realAuthId,即审核中,设置审核中标记位true,跳到realAuth_result.ftl
                model.addAttribute("auditing",true);
                return "realAuth_result";
            }
        }else{      //如果用户已经实名认证       //(3)根据userinfo上的realAuthId得到实名认证对象,放到model,设置审核中标记位false,跳到realAuth_result.ftl

            model.addAttribute("realAuth",realAuthService.get(current.getId()));
            model.addAttribute("auditing",false);
            return "realAuth_result";
        }




    }
}
