package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

/**
 * day09_02
 */
public interface PlatformBankInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlatformBankInfo record);

    PlatformBankInfo selectByPrimaryKey(Long id);

    List<PlatformBankInfo> selectAll();

    int updateByPrimaryKey(PlatformBankInfo record);

    /**分页条件查询总记录数*/
    int queryForCount(PlatformBankInfoQueryObject qo);

    /**分页条件查询数据list*/
    List<PlatformBankInfo> query(PlatformBankInfoQueryObject qo);


}