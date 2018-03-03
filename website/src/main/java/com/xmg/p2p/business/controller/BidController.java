package com.xmg.p2p.business.controller;

import com.xmg.p2p.base.annotation.RequireLogin;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.service.IBidRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投标相关控制层
 * @Author: Squalo
 * @Date: 2018/3/3 - 8:45       day09_05
 */
@Controller
@Slf4j
public class BidController {

    @Autowired
    private IBidRequestService bidRequestService;

    /**投资者投标申请*/
    @RequireLogin
    @RequestMapping("borrow_bid")
    @ResponseBody
    public JSONResult bid(Long bidRequestId, BigDecimal amount){
        JSONResult jsonResult = new JSONResult("恭喜你,投标成功!");
        try {
            bidRequestService.bid(bidRequestId, amount);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            jsonResult.setSuccess(false);
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }
}
