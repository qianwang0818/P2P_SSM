package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.qo.BidRequestQueryObject;

/**
 * 借款对象相关服务接口
 * @Author: Squalo
 * @Date: 2018/2/27 - 18:38     day08_01
 */
public interface IBidRequestService {

    void update(BidRequest bidRequest);

    /**检查用户是否达成借款的前置条件,如果有条件不满足就抛出异常*/
    void checkBidRequestPrecondition(Long logininfoId) throws RuntimeException;

    /**提交申请借款*/
    void apply(BidRequest form) throws RuntimeException;

    /**分页条件查询借款对象*/
    PageResult query(BidRequestQueryObject qo);
}
