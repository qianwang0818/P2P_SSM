package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.RealAuthQueryObject;

import java.util.List;

/**
 * day06_03
 */
public interface RealAuthMapper {
    int insert(RealAuth record);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth record);

    /**分页条件查询总记录数*/
    int queryForCount(RealAuthQueryObject qo);

    /**分页条件查询数据list*/
    List<RealAuth> query(RealAuthQueryObject qo);
}