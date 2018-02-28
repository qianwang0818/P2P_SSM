package com.xmg.mgrsite.businese.controller;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.qo.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 借款相关控制层
 * @Author: Squalo
 * @Date: 2018/2/28 - 17:18     day08_03
 */
@Controller
public class BidRequestController {

    @Autowired
    private IBidRequestService bidRequestService;

    @RequestMapping("bidrequest_publishaudit_list")
    public String bidrequestPublishAuditList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult",bidRequestService.query(qo));
        return "bidrequest/publish_audit";
    }

}
