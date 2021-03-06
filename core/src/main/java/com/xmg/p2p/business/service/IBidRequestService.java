package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.query.BidRequestQueryObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * 借款对象相关服务接口
 * @Author: Squalo
 * @Date: 2018/2/27 - 18:38     day08_01
 */
public interface IBidRequestService {

    /**根据主键id查询*/
    BidRequest get(Long id);

    void update(BidRequest bidRequest);

    /**检查用户是否达成借款的前置条件,如果有条件不满足就抛出异常*/
    void checkBidRequestPrecondition(Long logininfoId) throws RuntimeException;

    /**提交申请借款*/
    void apply(BidRequest form) throws RuntimeException;

    /**分页条件查询借款对象*/
    PageResult query(BidRequestQueryObject qo);

    /**发标前审核*/
    void publishAudit(BidRequestAuditHistory form);

    /**根据一个标的查询其对应的审核历史*/
    List<BidRequestAuditHistory> selectAuditHistoryByBidRequestId(Long bidRequestId);

    /**展示首页的借款标的*/
    List<BidRequest> listIndex();

    /**投资者投标申请*/
    void bid(Long bidRequestId, BigDecimal amount) throws RuntimeException ;

    /**满标一审审核*/
    void fullAudit1(BidRequestAuditHistory form);

    void fullAudit2(BidRequestAuditHistory form);
}
