package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.AccountFlow;
import java.util.List;

/**
 * day09_04
 */
public interface AccountFlowMapper {

    int insert(AccountFlow record);

    List<AccountFlow> selectAll();

}