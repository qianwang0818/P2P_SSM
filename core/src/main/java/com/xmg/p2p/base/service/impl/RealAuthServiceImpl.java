package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.RealAuthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 实名认证对象服务层 实现类
 * @Author: Squalo
 * @Date: 2018/2/23 - 12:17     day06_03
 */
@Service
public class RealAuthServiceImpl implements IRealAuthService {

    @Autowired
    private RealAuthMapper realAuthMapper;

    @Autowired
    private IUserinfoService userinfoService;

    @Override
    public RealAuth get(Long id) {
        return realAuthMapper.selectByPrimaryKey(id);
    }

    /**实名认证申请资料提交*/
    @Override
    public void apply(RealAuth realAuth) throws RuntimeException {
        Userinfo current = userinfoService.getCurrent();
        if(current.isRealAuth()){       //如果已经通过实名认证就抛异常
            throw new RuntimeException("您的实名认证已通过,请勿重复提交");
        }
        if(current.getRealAuthId()!=null){  //能到这里说明未通过实名认证,如果是待审核状态就抛异常
            throw new RuntimeException("您的实名认证申请正在审核中,请勿重复提交");
        }
        //能到这里说明: 当前用户没有通过实名认证并且当前用户不是待审状态,就可以保存实名认证对象.
        realAuth.setState(RealAuth.STATE_NORMAL);
        realAuth.setApplier(UserContext.getCurrent());
        realAuth.setApplyTime(new Date());
        realAuthMapper.insert(realAuth);
        //把实名认证对象的Id设置给userinfo
        current.setRealAuthId(realAuth.getId());
        userinfoService.update(current);
    }

    /**分页条件查询实名认证对象*/
    @Override
    public PageResult query(RealAuthQueryObject qo) {
        int totalCount = realAuthMapper.queryForCount(qo);
        if(totalCount<=0){
            return PageResult.empty(qo.getPageSize());
        }else{
            List<RealAuth> list = realAuthMapper.query(qo);
            return new PageResult(list,totalCount,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    /**实名认证的审核*/
    @Override
    public void audit(RealAuth form) throws RuntimeException {
        //根据id得到实名认证对象
        RealAuth realAuth = this.get(form.getId());

        //如果实名认证对象不存在就抛异常
        if(realAuth==null){
            throw new RuntimeException("该实名认证申请不存在");
        }

        //如果实名认证对象不是待审核状态(即审核通过or审核拒绝)就抛异常
        if(realAuth.getState()!=RealAuth.STATE_NORMAL){
            throw new RuntimeException("该实名认证申请已被审核,请刷新页面更新状态");
        }

        //能到这里说明实名认证对象存在并且对象处于待审核状态
        //1.设置实名认证对象的通用属性
        realAuth.setAuditor(UserContext.getCurrent());
        realAuth.setAuditTime(new Date());
        int state = form.getState();
        realAuth.setState(state);

        //得到申请者的userinfo. 如果用户已经实名认证就抛异常
        Userinfo applier = userinfoService.get(realAuth.getApplier().getId());
        if(applier.isRealAuth()){
            throw new RuntimeException("该前台用户已通过实名认证,请勿重复审核");
        }

        //能到这里说明申请者userinfo处于未通过实名认证状态
        //2.判断审核结果(通过or拒绝)
        if(state==RealAuth.STATE_AUDIT){             //如果状态是审核通过
            //添加实名认证的状态码,设置userinfo上的冗余数据
            applier.addState(BitStatesUtils.OP_REAL_AUTH);
            applier.setRealName(realAuth.getRealName());
            applier.setIdNumber(realAuth.getIdNumber());
            applier.setRealAuthId(realAuth.getId());
        }else if(state==RealAuth.STATE_REJECT){     //如果状态是审核拒绝
            //userinfo中的realAuthId置为null
            applier.setRealAuthId(null);
        }
        //3.执行更新操作
        realAuthMapper.updateByPrimaryKey(realAuth);
        userinfoService.update(applier);
    }


}
