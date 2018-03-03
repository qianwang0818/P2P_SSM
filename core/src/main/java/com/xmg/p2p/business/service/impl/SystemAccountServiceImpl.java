package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.SystemAccount;
import com.xmg.p2p.business.domain.SystemAccountFlow;
import com.xmg.p2p.business.mapper.SystemAccountFlowMapper;
import com.xmg.p2p.business.mapper.SystemAccountMapper;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.exception.BidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统账户相关服务
 * @author Administrator
 * @Date: 2018/3/3 - 15:00      day10_02
 */
@Service
public class SystemAccountServiceImpl implements ISystemAccountService {

	@Autowired
	private SystemAccountMapper systemAccountMapper;

	@Autowired
	private SystemAccountFlowMapper systemAccountFlowMapper;

	@Override
	public void update(SystemAccount systemAccount) {
		int ret = this.systemAccountMapper.updateByPrimaryKey(systemAccount);
		if (ret == 0) {
			throw new BidException("系统账户对象乐观锁失败");
		}
	}

	/**检查并初始化系统账户*/
	@Override
	public void initAccount() {
		SystemAccount current = this.systemAccountMapper.selectCurrent();
		if (current == null) {
			current = new SystemAccount();
			this.systemAccountMapper.insert(current);
		}
	}

	/**系统账户收到借款管理费*/
	@Override
	public void chargeBorrowFee(BidRequest br, BigDecimal managementChargeFee) {
		// 1.得到当前系统账户;
		SystemAccount current = this.systemAccountMapper.selectCurrent();
		// 2.修改系统账户余额;
		current.addUsableAmount(managementChargeFee);
		// 3.生成系统账户的收款流水
		SystemAccountFlow flow = new SystemAccountFlow(current,managementChargeFee,BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE,"借款" + br.getTitle() + "成功,收取借款手续费:" + managementChargeFee);
		this.systemAccountFlowMapper.insert(flow);	//系统账户的流水写入数据库
		// 4.更新系统账户
		this.update(current);
	}

	/*@Override
	public void chargeWithdrawFee(MoneyWithdraw m) {
		// 1,得到当前系统账户;
		SystemAccount current = this.systemAccountMapper.selectCurrent();
		// 2,修改账户余额;
		current.setUsableAmount(current.getUsableAmount().add(m.getCharge()));
		// 3,生成收款流水
		SystemAccountFlow flow = new SystemAccountFlow();
		flow.setAccountActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE);
		flow.setAmount(m.getCharge());
		flow.setBalance(current.getUsableAmount());
		flow.setCreatedDate(new Date());
		flow.setFreezedAmount(current.getFreezedAmount());
		flow.setNote("用户" + m.getApplier().getUsername() + "提现" + m.getAmount()
				+ "成功,收取提现手续费:" + m.getCharge());
		flow.setSystemAccountId(current.getId());
		this.systemAccountFlowMapper.insert(flow);
		this.update(current);
	}

	@Override
	public void chargeInterestFee(PaymentScheduleDetail psd,
			BigDecimal interestChargeFee) {
		// 1,得到当前系统账户;
		SystemAccount current = this.systemAccountMapper.selectCurrent();
		// 2,修改账户余额;
		current.setUsableAmount(current.getUsableAmount()
				.add(interestChargeFee));
		// 3,生成收款流水
		SystemAccountFlow flow = new SystemAccountFlow();
		flow.setAccountActionType(BidConst.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE);
		flow.setAmount(interestChargeFee);
		flow.setBalance(current.getUsableAmount());
		flow.setCreatedDate(new Date());
		flow.setFreezedAmount(current.getFreezedAmount());
		flow.setNote("用户收款" + psd.getTotalAmount() + "成功,收取利息管理费:"
				+ interestChargeFee);
		flow.setSystemAccountId(current.getId());
		this.systemAccountFlowMapper.insert(flow);
		this.update(current);
	}*/

}
