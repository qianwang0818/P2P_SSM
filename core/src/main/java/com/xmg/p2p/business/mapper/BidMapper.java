package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.Bid;
import java.util.List;

/**
 * day08_01
 */
public interface BidMapper {

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);

    //只是让BidRequestMapper发送一条额外sql查询List<Bid>使用,业务层不需要调用.
    //List selectByBidRequest(Long id);

}