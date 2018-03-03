package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.PaymentSchedule;

/**
 * day10_04
 */
public interface PaymentScheduleMapper {

	int insert(PaymentSchedule record);

	PaymentSchedule selectByPrimaryKey(Long id);

	int updateByPrimaryKey(PaymentSchedule record);

	//int queryForCount(PaymentScheduleQueryObject qo);

	//List<PaymentSchedule> query(PaymentScheduleQueryObject qo);
}