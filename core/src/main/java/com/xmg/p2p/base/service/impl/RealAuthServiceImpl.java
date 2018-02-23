package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.mapper.RealAuthMapper;
import com.xmg.p2p.base.service.IRealAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实名认证对象服务层 实现类
 * @Author: Squalo
 * @Date: 2018/2/23 - 12:17     day06_03
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {

    @Autowired
    private RealAuthMapper realAuthMapper;

    @Override
    public RealAuth get(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }
}
