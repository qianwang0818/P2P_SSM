package com.xmg.mgrsite.businese.controller;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.config.MyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 借款相关控制层
 * @Author: Squalo
 * @Date: 2018/2/28 - 17:18     day08_03
 */
@Controller
@Slf4j
public class BidRequestController {

    @Autowired
    private IBidRequestService bidRequestService;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IRealAuthService realAuthService;

    @Autowired
    private IUserFileService userFileService;

    /**显示待审核借款分页列表*/
    @RequestMapping("bidrequest_publishaudit_list")
    public String bidrequestPublishAuditList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/publish_audit";
    }

    /**发标前审核*/
    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JSONResult bidrequestPublishAudit(BidRequestAuditHistory form) {
        JSONResult jsonResult = new JSONResult("审核成功!");
        try {
            bidRequestService.publishAudit(form);   //传入参数 Id remark state
        } catch (RuntimeException e) {
            log.error("【BidRequestController:bidrequestPublishAudit】发送异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    /**
     * 后台查看借款人各种资料详情
     * @param id 借款对象的主键
     */
    @RequestMapping("borrow_info")
    public String borrowInfo(Long id, Model model){
        BidRequest bidRequest = bidRequestService.get(id);
        Long applierId = bidRequest.getCreateUser().getId();
        Userinfo userinfo = userinfoService.get(applierId);
        UserFileQueryObject qo = new UserFileQueryObject(applierId,UserFile.STATE_AUDIT,-1);
        List<UserFile> userFiles = userFileService.queryForList(qo);

        model.addAttribute("bidRequest",bidRequest);
        model.addAttribute("userinfo", userinfo);
        model.addAttribute("audits",bidRequestService.selectAuditHistoryByBidRequestId(id));     //审核历史
        model.addAttribute("realAuth",realAuthService.get(userinfo.getRealAuthId()));
        model.addAttribute("userFiles",userFiles);  //通过审核的风控资料信息
        model.addAttribute("websiteHost", MyConfig.websiteHost);
        return "bidrequest/borrow_info";
    }

    /**进入满标一审列表页面*/
    @RequestMapping("bidrequest_audit1_list")
    public String bidrequestFullAudit1List(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
        model.addAttribute("pageResult", bidRequestService.query(qo));
        return "bidrequest/audit1";
    }

    /**满标一审审核*/
    @RequestMapping("bidrequest_audit1")
    @ResponseBody
    public JSONResult bidrequestAudit1(BidRequestAuditHistory form) {
        JSONResult jsonResult = new JSONResult("审核成功!");
        try {
            bidRequestService.fullAudit1(form);   //传入参数 Id remark state
        } catch (RuntimeException e) {
            log.error("【BidRequestController:bidrequestAudit1】发送异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

}
