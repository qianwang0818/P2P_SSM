package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.PlatformBankInfoMapper;
import com.xmg.p2p.business.mapper.RechargeOfflineMapper;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.service.IRechargeOfflineService;
import com.xmg.p2p.exception.BidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountFlowService accountFlowService;

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

    @Override
    public void audit(RechargeOffline form) throws RuntimeException {
        int state = form.getState();    //线下充值单的审核结果(通过or拒绝)
        RechargeOffline ro = rechargeOfflineMapper.selectByPrimaryKey(form.getId());

        //判断是不是有这个线下充值对象,没有就抛异常
        if(ro ==null){
            throw new BidException("该线下充值单不存在,请刷新页面!");
        }
        //判断这个线下充值对象是不是待审核状态,不是就抛异常
        if(ro.getState()!=RechargeOffline.STATE_NORMAL){
            throw new BidException("该线下充值单已被审核,请刷新页面!");
        }

        //给线下充值单填充审核属性
        ro.setAuditor(UserContext.getCurrent());
        ro.setAuditTime(new Date());
        ro.setRemark(form.getRemark());
        ro.setState(state);
        rechargeOfflineMapper.updateByPrimaryKey(ro);

        //判断审核结果. 如果成功. 给用户的账户余额加钱,生成资金交易流水
        if(state == RechargeOffline.STATE_AUDIT){
            Account account = accountService.get(ro.getApplier().getId());
            account.addUsableAmount(ro.getAmount());
            accountFlowService.rechargeFlow(ro, account);   //调用流水Service生成流水记录
            accountService.update(account);
        }
    }
}
