package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;

/**
 * 线下充值服务层接口
 * @Author: Squalo
 * @Date: 2018/3/2 - 13:14      day09_03
 */
public interface IRechargeOfflineService {
    /**提交线下充值单申请*/
    void apply(RechargeOffline rechargeOffline);

    /**分页条件查询借款对象*/
    PageResult query(RechargeOfflineQueryObject qo);
}
