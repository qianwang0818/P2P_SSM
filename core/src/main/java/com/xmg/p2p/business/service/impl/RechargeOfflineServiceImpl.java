package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.PlatformBankInfoMapper;
import com.xmg.p2p.business.mapper.RechargeOfflineMapper;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;
import com.xmg.p2p.exception.BidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 线下充值服务层接口
 * @Author: Squalo
 * @Date: 2018/3/2 - 13:17      day09_03
 */
@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {

    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;

    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    @Override
    public void apply(RechargeOffline ro) {
        PlatformBankInfo bankInfo = ro.getBankInfo();
        if(bankInfo==null || bankInfo.getId()==null || platformBankInfoMapper.selectByPrimaryKey(bankInfo.getId())==null){
            throw new BidException("该平台账户不存在,请刷新页面后重试!");
        }
        ro.setApplier(UserContext.getCurrent());
        ro.setApplyTime(new Date());
        ro.setState(RechargeOffline.STATE_NORMAL);
        rechargeOfflineMapper.insert(ro);
    }

    @Override
    public PageResult query(RechargeOfflineQueryObject qo) {
        int totalCount = rechargeOfflineMapper.queryForCount(qo);
        if(totalCount<=0){
            return PageResult.empty(qo.getPageSize());
        }else{
            List<RechargeOffline> list = rechargeOfflineMapper.query(qo);
            return new PageResult(list,totalCount,qo.getCurrentPage(),qo.getPageSize());
        }
    }
}
