package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户资金流水
 * @Author: Squalo
 * @Date: 2018/3/2 - 15:10      day09_04
 */
@Data
@NoArgsConstructor
public class AccountFlow extends BaseDomain {

    private Long accountId;                 //流水是关于哪个账户的
    private BigDecimal amount;              //流水金额
    private Date tradeTime;                 //流水时间
    private int flowType;                   //资金流水类型
    private BigDecimal usableAmount;        //发生变化之后的可用金额
    private BigDecimal freezedAmount;       //发生变化之后的可冻结金额
    private String note;                     //流水说明

    public AccountFlow(Long accountId, BigDecimal amount, Date tradeTime, int flowType, BigDecimal usableAmount, BigDecimal freezedAmount, String note) {
        this.accountId = accountId;
        this.amount = amount;
        this.tradeTime = tradeTime;
        this.flowType = flowType;
        this.usableAmount = usableAmount;
        this.freezedAmount = freezedAmount;
        this.note = note;
    }

    public AccountFlow(Account account, BigDecimal amount, int flowType, String note) {
        this.accountId = account.getId();
        this.amount = amount;
        this.tradeTime = new Date();
        this.flowType = flowType;
        this.usableAmount = account.getUsableAmount();
        this.freezedAmount = account.getFreezedAmount();
        this.note = note;
    }

}
