package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.business.domain.UserBankInfo;
import com.xmg.p2p.business.mapper.UserBankinfoMapper;
import com.xmg.p2p.business.service.IUserBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户绑定银行卡 相关服务实现
 * @Author: Squalo
 * @Date: 2018/3/5 - 20:54      day11_01
 */
@Service
public class UserBankServiceImpl implements IUserBankService {

    @Autowired
    private UserBankinfoMapper userBankinfoMapper;


    @Override
    public UserBankInfo selectByUser(Long id) {
        return userBankinfoMapper.selectByUser(id);
    }
}
