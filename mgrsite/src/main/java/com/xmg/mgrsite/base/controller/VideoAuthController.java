package com.xmg.mgrsite.base.controller;

import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoAuthQueryObject;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVideoService;
import com.xmg.p2p.base.util.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证控制层
 * @Author: Squalo
 * @Date: 2018/2/24 - 18:53     day06_10
 */
@Controller
@Slf4j
public class VideoAuthController {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private ILogininfoService logininfoService;

    /**点击菜单栏跳转到视频审核页面*/
    @RequestMapping("videoAuth")
    public String videoAuth(@ModelAttribute("qo")VideoAuthQueryObject qo, Model model){
        model.addAttribute("pageResult",videoService.query(qo));
        return "videoAuth/list";
    }

    /**增加视频审核记录*/
    @RequestMapping("videoAuth_audit")
    @ResponseBody
    public JSONResult videoAuthAudit(VideoAuth videoAuth){
        JSONResult jsonResult = new JSONResult("审核成功!");
        try {
            videoService.audit(videoAuth);      //实际只有id,remark,state 数据
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    /**用于用户的用户名自动补全*/
    @RequestMapping("videoAuth_autoComplete")
    @ResponseBody
    public List<Map<String,Object>> AutoComplete(String keyword){
        return logininfoService.autoComplete(keyword);
    }

}
