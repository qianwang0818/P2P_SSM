package com.xmg.p2p.business.controller;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 前台用户首页
 * @Author: Squalo
 * @Date: 2018/3/1 - 10:55      day08_06
 */
@Controller
public class IndexController {

    @Autowired
    private IBidRequestService bidRequestService;

    /**进入首页页面*/
    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("bidRequests",bidRequestService.listIndex());
        return "main";
    }
}
