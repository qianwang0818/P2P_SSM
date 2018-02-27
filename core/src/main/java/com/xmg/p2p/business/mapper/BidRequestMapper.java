package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.BidRequest;
import java.util.List;

/**
 * day08_01
 */
public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);

    //List<BidRequest> selectAll();

    int updateByPrimaryKey(BidRequest record);

}