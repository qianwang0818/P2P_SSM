package com.xmg.mgrsite.base.controller;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据字典表相关控制层
 * @Author: Squalo
 * @Date: 2018/2/20 - 23:18     day05_06
 */
@Controller
@Slf4j
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    /**分页展示数据字典分类列表*/
    @RequestMapping("systemDictionary_list")
    public String systemDictionary_list(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult pageResult = systemDictionaryService.queryDictionary(qo);
        model.addAttribute("pageResult",pageResult);
        return "systemdic/systemDictionary_list";
    }

    /**增加or修改数据字典分类*/
    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public JSONResult systemDictionaryUpdate(SystemDictionary systemDictionary){
        JSONResult jsonResult = new JSONResult("保存成功");
        try {
            systemDictionaryService.saveOrUpdateDictionary(systemDictionary);
        } catch (Exception e) {
            log.error("【SystemDictionaryController:systemDictionaryUpdate】发生异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg("保存失败");
        }
        return jsonResult;
    }

    /**分页展示数据字典明细列表*/
    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItem_list(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){
        PageResult pageResult = systemDictionaryService.queryDictionaryItem(qo);
        List<SystemDictionary> systemDictionaryList = systemDictionaryService.selectAllSystemDictionary();
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("systemDictionaryGroups",systemDictionaryList);
        return "systemdic/systemDictionaryItem_list";
    }

    /**增加or修改数据字典分类*/
    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JSONResult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem){
        JSONResult jsonResult = new JSONResult("保存成功");
        try {
            systemDictionaryService.saveOrUpdateDictionaryItem(systemDictionaryItem);
        } catch (Exception e) {
            log.error("【SystemDictionaryController:systemDictionaryItemUpdate】发生异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg("保存失败");
        }
        return jsonResult;
    }
}
