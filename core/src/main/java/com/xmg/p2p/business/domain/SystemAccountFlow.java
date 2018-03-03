package com.xmg.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.xmg.p2p.base.domain.BaseDomain;

/**
 * 系统账户流水
 * @author Administrator
 * @Date: 2018/3/3 - 15:00      day10_02
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemAccountFlow extends BaseDomain {

	private Date createdDate;               // 流水创建时间
	private int accountActionType;         // 系统账户流水类型
	private BigDecimal amount;               // 流水相关金额
	private String note;                     // 流水说明
	private BigDecimal usableAmount;        // 流水产生之后系统账户的可用余额;
	private BigDecimal freezedAmount;       // 流水产生之后系统账户的冻结余额;
	private Long systemAccountId;           // 对应的系统账户的id

	public SystemAccountFlow(Date createdDate, int accountActionType, BigDecimal amount, String note, BigDecimal usableAmount, BigDecimal freezedAmount, Long systemAccountId) {
		this.createdDate = createdDate;
		this.accountActionType = accountActionType;
		this.amount = amount;
		this.note = note;
		this.usableAmount = usableAmount;
		this.freezedAmount = freezedAmount;
		this.systemAccountId = systemAccountId;
	}
	public SystemAccountFlow(SystemAccount systemAccount, BigDecimal amount, int flowType, String note) {
		this.createdDate = new Date();;
		this.accountActionType = accountActionType;
		this.amount = amount;
		this.note = note;
		this.usableAmount = systemAccount.getUsableAmount();
		this.freezedAmount = systemAccount.getFreezedAmount();
		this.systemAccountId = systemAccount.getId();
	}
}
