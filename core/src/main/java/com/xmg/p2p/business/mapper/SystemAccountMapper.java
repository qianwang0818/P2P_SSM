package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.SystemAccount;

/**
 * 系统账户
 * @author Administrator
 * @Date: 2018/3/3 - 15:00      day10_02
 */
public interface SystemAccountMapper {

	int insert(SystemAccount record);

	/**返回当前活动的那个系统账户*/
	SystemAccount selectCurrent();

	int updateByPrimaryKey(SystemAccount record);
}