package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 登录日志Controller
 * @Author: Squalo
 * @Date: 2018/2/17 - 16:07     day03_10
 */
@Controller
public class IplogController {

    @Autowired
    private IIplogService iplogService;

    /**个人用户登录记录列表*/
    @RequireLogin
    @RequestMapping("ipLog")
    public ModelAndView ipLogList(@ModelAttribute("qo") IplogQueryObject iplogQO, Map<String,Object> map){
        iplogQO.setUsername(UserContext.getCurrent().getUsername());
        PageResult pageResult = iplogService.query(iplogQO);
        map.put("pageResult", pageResult);
        return new ModelAndView("iplog_list",map);
    }
}
