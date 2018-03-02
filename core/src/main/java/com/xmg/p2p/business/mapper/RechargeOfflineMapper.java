package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;

import java.util.List;

/**
 * day09_03
 */
public interface RechargeOfflineMapper {

    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);

    List<RechargeOffline> selectAll();

    int updateByPrimaryKey(RechargeOffline record);

    /**分页条件查询总记录数*/
    int queryForCount(RechargeOfflineQueryObject qo);

    /**分页条件查询数据list*/
    List<RechargeOffline> query(RechargeOfflineQueryObject qo);
}