package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BidConst;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户对应的账户信息
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:48    day03_03
 */

@Data
public class Account extends BaseDomain {

    private int version;
    private String tradePassword;
    private BigDecimal usableAmount = BidConst.ZERO ;
    private BigDecimal freezedAmount = BidConst.ZERO ;
    private BigDecimal unReceiveInterest = BidConst.ZERO ;
    private BigDecimal unReceivePrincipal = BidConst.ZERO ;
    private BigDecimal unReturnAmount = BidConst.ZERO ;
    private BigDecimal remainBorrowLimit = BidConst.INIT_BRROW_LIMIT ;
    private BigDecimal borrowLimit = BidConst.INIT_BRROW_LIMIT ;

    public BigDecimal getTotalAmount(){
        return usableAmount.add(freezedAmount).add(unReceivePrincipal);
    }
}
