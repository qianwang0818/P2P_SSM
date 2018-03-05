package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.UserBankInfo;
import com.xmg.p2p.business.mapper.UserBankinfoMapper;
import com.xmg.p2p.business.service.IUserBankService;
import com.xmg.p2p.exception.BidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台用户绑定银行卡 相关服务实现
 * @Author: Squalo
 * @Date: 2018/3/5 - 20:54      day11_01
 */
@Service
public class UserBankServiceImpl implements IUserBankService {

    @Autowired
    private UserBankinfoMapper userBankinfoMapper;

    @Autowired
    private IUserinfoService userinfoService;

    /**得到当前用户绑定的银行卡信息*/
    @Override
    public UserBankInfo selectByUser(Long id) {
        return userBankinfoMapper.selectByUser(id);
    }

    /**绑定银行卡*/
    @Override
    public void bind(UserBankInfo userBankInfo) throws RuntimeException {
        //判断当前用户是否已绑定
        Userinfo userinfo = userinfoService.getCurrent();
        if(userinfo.isBindBank()){
            throw new BidException("您已绑定银行卡!");
        }
        if(!userinfo.isRealAuth()){
            throw new BidException("您尚未完成实名认证!");
        }

        //填充userBankInfo对象
        userBankInfo.setAccountName(userinfo.getRealName());
        userBankInfo.setLogininfo(UserContext.getCurrent());
        userBankinfoMapper.insert(userBankInfo);

        //修改用户状态码
        userinfo.addState(BitStatesUtils.OP_BIND_BANKINFO);
        userinfoService.update(userinfo);
    }
}
