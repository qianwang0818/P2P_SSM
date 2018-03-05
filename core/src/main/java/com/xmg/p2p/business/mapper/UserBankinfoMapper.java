package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.UserBankInfo;

/**
 * day11_01
 */
public interface UserBankinfoMapper {

    int insert(UserBankInfo userBankInfo);

    UserBankInfo selectByUser(Long logininfoId);

}