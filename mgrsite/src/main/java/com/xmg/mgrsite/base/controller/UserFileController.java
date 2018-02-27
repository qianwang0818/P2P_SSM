package com.xmg.mgrsite.base.controller;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 风控资料审核控制层
 * @Author: Squalo
 * @Date: 2018/2/26 - 17:31     day07_03
 */
@Controller
@Slf4j
public class UserFileController {

    @Autowired
    private IUserFileService userFileService;

    /**跳转到风控材料列表审核中心*/
    @RequestMapping("userFileAuth")
    public String userFileAuth(@ModelAttribute("qo")UserFileQueryObject qo, Model model){
        model.addAttribute("pageResult",userFileService.query(qo));
        return "userFileAuth/list";
    }

    /**审核风控认证材料*/
    @RequestMapping("userFile_audit")
    @ResponseBody
    public JSONResult audit(UserFile form){
        JSONResult jsonResult = new JSONResult("审核成功!");
        try {
            userFileService.audit(form);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

}
