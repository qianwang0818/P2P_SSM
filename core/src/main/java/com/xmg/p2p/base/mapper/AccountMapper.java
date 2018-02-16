package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Account;
import java.util.List;

/***
 * day03_04
 */
public interface AccountMapper {

    int insert(Account record);

    Account selectByPrimaryKey(Long id);

    List<Account> selectAll();

    int updateByPrimaryKey(Account record);
}