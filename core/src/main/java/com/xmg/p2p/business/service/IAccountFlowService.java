package com.xmg.p2p.business.service;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.RechargeOffline; /**
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
}
