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

    public BigDecimal addUsableAmount(BigDecimal increase){
        this.usableAmount = this.usableAmount.add(increase);
        return this.usableAmount;
    }
    public BigDecimal subtractUsableAmount(BigDecimal decrease){
        this.usableAmount = this.usableAmount.subtract(decrease);
        return this.usableAmount;
    }

    public BigDecimal addFreezedAmount(BigDecimal increase){
        this.freezedAmount = this.freezedAmount.add(increase);
        return this.freezedAmount;
    }
    public BigDecimal subtractFreezedAmount(BigDecimal decrease){
        this.freezedAmount = this.freezedAmount.subtract(decrease);
        return this.freezedAmount;
    }

    public BigDecimal addUnReturnAmount(BigDecimal increase){
        this.unReturnAmount = this.unReturnAmount.add(increase);
        return this.unReturnAmount;
    }
    public BigDecimal subtractUnReturnAmount(BigDecimal decrease){
        this.unReturnAmount = this.unReturnAmount.subtract(decrease);
        return this.unReturnAmount;
    }

    public BigDecimal addRemainBorrowLimit(BigDecimal increase){
        this.remainBorrowLimit = this.remainBorrowLimit.add(increase);
        return this.remainBorrowLimit;
    }
    public BigDecimal subtractRemainBorrowLimit(BigDecimal decrease){
        this.remainBorrowLimit = this.remainBorrowLimit.subtract(decrease);
        return this.remainBorrowLimit;
    }

    public BigDecimal addUnReceivePrincipal(BigDecimal increase){
        this.unReceivePrincipal = this.unReceivePrincipal.add(increase);
        return this.unReceivePrincipal;
    }
    public BigDecimal subtractUnReceivePrincipal(BigDecimal decrease){
        this.unReceivePrincipal = this.unReceivePrincipal.subtract(decrease);
        return this.unReceivePrincipal;
    }

    public BigDecimal addUnReceiveInterest(BigDecimal increase){
        this.unReceiveInterest = this.unReceiveInterest.add(increase);
        return this.unReceiveInterest;
    }
    public BigDecimal subtractUnReceiveInterest(BigDecimal decrease){
        this.unReceiveInterest = this.unReceiveInterest.subtract(decrease);
        return this.unReceiveInterest;
    }

}
