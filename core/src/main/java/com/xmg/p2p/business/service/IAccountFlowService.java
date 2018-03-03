package com.xmg.p2p.business.service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.RechargeOffline;

import java.math.BigDecimal; /**
 * 账户流水的业务层接口
 * @Author: Squalo
 * @Date: 2018/3/2 - 15:49      day09_04
 */
public interface IAccountFlowService {

    /**充值流水*/
    void rechargeFlow(RechargeOffline rechargeOffline, Account account);

    /**投标流水*/
    void bidFlow(Bid bid, Account account);

    /**满标审核拒绝,退款流水*/
    void returnBidMoney(Bid bid, Account account);

    /**借款人借款成功,收款的流水*/
    void borrowSuccessFlow(BidRequest br, Account applierAccount);

    /**借款人缴纳借款手续费流水*/
    void borrowChargeFeeFlow(BigDecimal managementChargeFee, BidRequest br, Account applierAccount);

    /**投标成功,投资人冻结金额减少的流水*/
    void bidSuccessFlow(Bid bid, Account bidAccount);
}
