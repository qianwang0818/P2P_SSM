package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.qo.BidRequestQueryObject;

import java.util.List;

/**
 * day08_01
 */
public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    //List<BidRequest> selectAll();

    int updateByPrimaryKey(BidRequest record);

    /**分页条件查询总记录数*/
    int queryForCount(BidRequestQueryObject qo);

    /**分页条件查询数据list*/
    List<BidRequest> query(BidRequestQueryObject qo);

}