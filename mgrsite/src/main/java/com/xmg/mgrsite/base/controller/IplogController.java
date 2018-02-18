package com.xmg.mgrsite.base.controller;

import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台用户查询登录日志控制层
 * @Author: Squalo
 * @Date: 2018/2/18 - 14:38     day04_04
 */
@Controller
public class IplogController {

    @Autowired
    private IIplogService iplogService;

    @RequestMapping("ipLog")
    public String iplog(@ModelAttribute("qo") IplogQueryObject iplogQO, Model model){
        PageResult pageResult = iplogService.query(iplogQO);
        model.addAttribute("pageResult",pageResult);
        return "iplog/list";
    }
}
