package com.xmg.mgrsite.businese.controller;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.qo.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            bidRequestService.publishAudit(form);
        } catch (RuntimeException e) {
            log.error("【BidRequestController:bidrequestPublishAudit】发送异常:{}",e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}
