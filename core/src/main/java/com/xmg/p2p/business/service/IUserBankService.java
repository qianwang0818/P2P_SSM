package com.xmg.p2p.business.service;

import com.xmg.p2p.business.domain.UserBankInfo;

/**
 * 前台用户绑定银行卡 相关服务接口
 * @Author: Squalo
 * @Date: 2018/3/5 - 20:54      day11_01
 */
public interface IUserBankService {

    /**得到当前用户绑定的银行卡信息*/
    UserBankInfo selectByUser(Long id);

    /**绑定银行卡*/
    void bind(UserBankInfo userBankInfo) throws RuntimeException;
}
