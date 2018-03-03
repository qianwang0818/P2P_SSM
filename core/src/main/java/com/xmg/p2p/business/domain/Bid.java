package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次投标对象.
 * 一个标的对应多次投资,一个借款对象对应多个投标对象.
 * @Author: Squalo
 * @Date: 2018/2/27 - 17:43     day08_01
 */
@Data
@NoArgsConstructor
public class Bid extends BaseDomain {
    private BigDecimal actualRate;              // 年化利率(冗余数据,等同于BidRequest上的currentRate)
    private BigDecimal availableAmount;        // 此次投标金额
    private Long bidRequestId;                 // 关联借款对象的主键,等同于BidRequest上的id
    private String bidRequestTitle;            // 关联借款对象的标题.冗余数据,等同于BidRequest上的借款标题
    private Logininfo bidUser;                  // 投标人logininfo
    private Date bidTime;                       // 投标时间
    private int bidRequestState;              // 借款状态.不保存到数据库中,只供查询使用.冗余数据,等同于BidRequest上的bidRequestState.

    public Bid(BigDecimal actualRate, BigDecimal availableAmount, Long bidRequestId, String bidRequestTitle, Logininfo bidUser, Date bidTime) {
        this.actualRate = actualRate;
        this.availableAmount = availableAmount;
        this.bidRequestId = bidRequestId;
        this.bidRequestTitle = bidRequestTitle;
        this.bidUser = bidUser;
        this.bidTime = bidTime;
    }
}
