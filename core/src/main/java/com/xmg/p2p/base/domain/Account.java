package com.xmg.p2p.base.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户对应的账户信息
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:48
 */

@Data
public class Account extends BaseDomain {

    private int version;
    private String tradePassword;
    private BigDecimal usableAmount;
    private BigDecimal freezedAmount;
    private BigDecimal unReceiveInterest;
    private BigDecimal unReceivePrincipal;
    private BigDecimal unReturnAmount;
    private BigDecimal remainBorrowLimit;
    private BigDecimal borrowLimit;

}
