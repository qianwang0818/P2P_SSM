package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关信息服务
 * @Author: Squalo
 * @Date: 2018/2/16 - 18:17     day03_04
 */
@Service
@Slf4j
public class UserinfoServiceImpl implements IUserinfoService {

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private IVerifyCodeService verifyCodeService;

    @Override
    public void update(Userinfo userinfo) {
        int ret = userinfoMapper.updateByPrimaryKey(userinfo);
        if(ret == 0){       //乐观锁失败
            throw new RuntimeException("【Userinfo更新操作】乐观锁失败,Userinfo:"+userinfo.getId());   //TODO 抛出自定义异常
        }
    }

    @Override
    public void add(Userinfo userinfo) {
        userinfoMapper.insert(userinfo);
    }

    @Override
    public Userinfo get(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    /**用户手机认证,其中调用IVerifyCodeService校验Redis验证码业务*/
    @Override
    public void bindPhone(VerifyCodeVO verifyCodeVO) throws RuntimeException {
        //先判断用户是否已经绑定了手机,如果已经绑定了手机,抛出异常
        Userinfo userinfo = this.get(UserContext.getCurrent().getId());
        if(userinfo!=null && userinfo.isBindPhone()){      //如果用户已经绑定了手机
            throw new RuntimeException("您已绑定手机,请勿重复绑定");       //TODO 抛出自定义异常
        }

        //调用IVerifyCodeService业务,校验比对Redis.
        verifyCodeService.verifyCode(verifyCodeVO); //期间如果验证失败会有异常抛出,后面就不会继续执行

        //如果验证码正确,执行认证手机逻辑
        log.info("【UserinfoServiceImpl:bindPhone】校验验证码成功,IVerifyCodeService的校验验证码方法没有抛出异常");
        userinfo.addState(BitStatesUtils.OP_BIND_PHONE);
        userinfo.setPhoneNumber(verifyCodeVO.getPhoneNumber());
        this.update(userinfo);
        log.info("【UserinfoServiceImpl:bindPhone】Userinfo更新成功,乐观锁未抛出异常");
    }

}
