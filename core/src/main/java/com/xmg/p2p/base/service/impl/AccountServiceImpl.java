package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户相关服务
 * @Author: Squalo
 * @Date: 2018/2/16 - 18:13    day03_04
 */
@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void update(Account account) {
        int ret = accountMapper.updateByPrimaryKey(account);
        if(ret == 0){       //乐观锁失败
            throw new RuntimeException("乐观锁失败,Account:"+account.getId());   //TODO 抛出自定义异常
        }
    }

    @Override
    public void add(Account account) {
        accountMapper.insert(account);
    }

    @Override
    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    @Override
    public Account getCurrent() {
        return this.get(UserContext.getCurrent().getId());
    }
}
