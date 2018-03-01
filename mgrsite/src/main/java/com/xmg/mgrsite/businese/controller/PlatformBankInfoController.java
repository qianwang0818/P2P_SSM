package com.xmg.mgrsite.businese.controller;

import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 平台银行账户管理 控制层
 * @Author: Squalo
 * @Date: 2018/3/1 - 20:01      day09_02
 */
@Controller
public class PlatformBankInfoController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    /**显示平台银行账户列表页面*/
    @RequestMapping("platformBankInfo_list")
    public String platformBankInfoList(@ModelAttribute("qo")PlatformBankInfoQueryObject qo, Model model){
        model.addAttribute("pageResult",platformBankInfoService.query(qo));
        return "platformbankinfo/list";
    }

    /**保存或修改平台银行账户*/
    @RequestMapping("platformBankInfo_saveorupdate")
    public String platformBankInfoSaveOrUpdate(PlatformBankInfo platformBankInfo){
        platformBankInfoService.saveOrUpdate(platformBankInfo);
        return "redirect:platformBankInfo_list.do";
    }
}
