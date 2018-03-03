package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.AccountFlow;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.AccountFlowMapper;
import com.xmg.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        AccountFlow flow = new AccountFlow(account, bid.getAvailableAmount(), BidConst.BIDREQUEST_STATE_BIDDING, "标的"+ bid.getBidRequestTitle()+"-投标成功,冻结账户可用余额:" + bid.getAvailableAmount());
        accountFlowMapper.insert(flow);
    }

    /**借款人借款成功,收款的流水
     * day09_06*/
    @Override
    public void returnBidMoney(Bid bid, Account account) {
        AccountFlow flow = new AccountFlow(account, bid.getAvailableAmount(), BidConst.ACCOUNT_ACTIONTYPE_BID_UNFREEZED, "标的"+ bid.getBidRequestTitle()+"-满审拒绝退款:" + bid.getAvailableAmount());
        accountFlowMapper.insert(flow);
    }

    /**借款人借款成功,收款的流水
     * day10_02*/
    @Override
    public void borrowSuccessFlow(BidRequest bidRequest, Account account) {
        AccountFlow flow = new AccountFlow(account, bidRequest.getBidRequestAmount(), BidConst.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL, "标的"+ bidRequest.getTitle()+"-借款成功,收到借款金:" + bidRequest.getBidRequestAmount());
        accountFlowMapper.insert(flow);
    }

    /**借款人缴纳借款手续费流水*/
    @Override
    public void borrowChargeFeeFlow(BigDecimal managementChargeFee, BidRequest bidRequest, Account account) {
        AccountFlow flow = new AccountFlow(account, managementChargeFee, BidConst.ACCOUNT_ACTIONTYPE_CHARGE, "标的"+ bidRequest.getTitle()+"-借款成功,支付借款手续费:" + managementChargeFee );
        accountFlowMapper.insert(flow);
    }

    /**投标成功,投资人冻结金额减少的流水*/
    @Override
    public void bidSuccessFlow(Bid bid, Account account) {
        AccountFlow flow = new AccountFlow(account, bid.getAvailableAmount(), BidConst.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL, "标的"+ bid.getBidRequestTitle()+"-投资成功,本次投标金额:" + bid.getAvailableAmount());
        accountFlowMapper.insert(flow);
    }
}
