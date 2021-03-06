package com.xmg.p2p.business.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.util.BidConst;

/**
 * 系统账户
 * @author Administrator
 * @Date: 2018/3/3 - 15:00      day10_02
 */
@Setter
@Getter
public class SystemAccount extends BaseDomain {
	private int version;// 版本
	private BigDecimal usableAmount = BidConst.ZERO;// 平台账户剩余金额
	private BigDecimal freezedAmount = BidConst.ZERO;// 平台账户冻结金额

    public BigDecimal addUsableAmount(BigDecimal increase){
        this.usableAmount = this.usableAmount.add(increase);
        return this.usableAmount;
    }
    public BigDecimal subtractUsableAmount(BigDecimal decrease){
        this.usableAmount = this.usableAmount.subtract(decrease);
        return this.usableAmount;
    }
}
