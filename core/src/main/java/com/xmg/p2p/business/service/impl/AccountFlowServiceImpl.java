package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.AccountFlow;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.AccountFlowMapper;
import com.xmg.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 账户流水的业务层实现类
 * @Author: Squalo
 * @Date: 2018/3/2 - 15:51      day09_04
 */
@Service
public class AccountFlowServiceImpl implements IAccountFlowService {

    @Autowired
    private AccountFlowMapper accountFlowMapper;

    /**充值流水
     * day09_04*/
    @Override
    public void rechargeFlow(RechargeOffline ro, Account account) {
        AccountFlow flow = new AccountFlow(account,ro.getAmount(),BidConst.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE,"线下充值成功,增加可用余额:"+ro.getAmount());
        accountFlowMapper.insert(flow);
    }

    /**投标流水
     * day09_05*/
    @Override
    public void bidFlow(Bid bid, Account account) {
        AccountFlow flow = new AccountFlow(account, bid.getAvailableAmount(), BidConst.BIDREQUEST_STATE_BIDDING, "投标成功,冻结账户可用余额:" + bid.getAvailableAmount());
        accountFlowMapper.insert(flow);
    }
}
