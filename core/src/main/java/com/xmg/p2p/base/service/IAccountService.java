package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Account;

/**
 * 账户相关服务
 * @Author: Squalo
 * @Date: 2018/2/16 - 18:12    day03_04
 */
public interface IAccountService {

    /**支持乐观锁的更新方法*/
    void update(Account account);
}
