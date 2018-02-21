package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户个人资料填写控制层
 * @Author: Squalo
 * @Date: 2018/2/21 - 15:04     day06_01
 */
@Controller
@Slf4j
public class BasicInfoController {

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequireLogin
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        model.addAttribute("userinfo",userinfoService.getCurrent());
        model.addAttribute("educationBackgrounds",systemDictionaryService.selectItemByParentSn("educationBackground"));
        model.addAttribute("incomeGrades",systemDictionaryService.selectItemByParentSn("incomeGrade"));
        model.addAttribute("marriages",systemDictionaryService.selectItemByParentSn("marriage"));
        model.addAttribute("kidCounts",systemDictionaryService.selectItemByParentSn("kidCount"));
        model.addAttribute("houseConditions",systemDictionaryService.selectItemByParentSn("houseCondition"));
        return "userInfo";
    }

    @RequireLogin
    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JSONResult basicInfoSave(Userinfo userinfo){
        JSONResult jsonResult = new JSONResult("保存成功");
        try {
            userinfoService.updateBasicInfo(userinfo);
        } catch (Exception e) {
            log.error("【BasicInfoController:basicInfoSave】发送异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg("保存失败");
        }
        return jsonResult;
    }
}
