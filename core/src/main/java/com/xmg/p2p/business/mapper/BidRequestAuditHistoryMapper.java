package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import java.util.List;

/**
 * day08_03
 */
public interface BidRequestAuditHistoryMapper {

    int insert(BidRequestAuditHistory record);

    BidRequestAuditHistory selectByPrimaryKey(Long id);

    List<BidRequestAuditHistory> selectByBidRequestId(Long id);

}