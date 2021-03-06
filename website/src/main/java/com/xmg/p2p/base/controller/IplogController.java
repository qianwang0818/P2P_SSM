package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 前台登录日志Controller
 * @Author: Squalo
 * @Date: 2018/2/17 - 16:07     day03_10
 */
@Controller
public class IplogController {

    @Autowired
    private IIplogService iplogService;

    /**非异步个人用户登录记录列表*/
    @RequireLogin
    @RequestMapping("ipLog")
    public ModelAndView ipLogList(@ModelAttribute("qo") IplogQueryObject iplogQO, Map<String,Object> map){
        iplogQO.setUsername(UserContext.getCurrent().getUsername());
        PageResult pageResult = iplogService.query(iplogQO);
        map.put("pageResult", pageResult);
        return new ModelAndView("iplog",map);
    }


    /**异步获取个人用户登录记录列表
     * day08_07 改造成异步获取HTML文本方式刷新列表
     */
    @RequireLogin
    @RequestMapping("ipLog_ajax")
    public ModelAndView ipLogListAjax(@ModelAttribute("qo") IplogQueryObject iplogQO, Map<String,Object> map){
        iplogQO.setUsername(UserContext.getCurrent().getUsername());
        PageResult pageResult = iplogService.query(iplogQO);
        map.put("pageResult", pageResult);
        return new ModelAndView("iplog_list",map);
    }


}
