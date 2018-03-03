package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import com.xmg.p2p.base.vo.VerifyEmailVO;
import com.xmg.p2p.exception.BidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    private IVerifyService verifyService;

    @Override
    public void update(Userinfo userinfo) {
        int ret = userinfoMapper.updateByPrimaryKey(userinfo);
        if(ret == 0){       //乐观锁失败
            throw new BidException("【Userinfo更新操作】乐观锁失败,Userinfo:"+userinfo.getId());
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

    /**得到当前Session中的Logininfo对应的Userinfo*/
    @Override
    public Userinfo getCurrent() {
        return this.get(UserContext.getCurrent().getId());
    }


    /**用户手机认证,其中调用IVerifyCodeService校验Redis验证码业务*/
    @Override
    public void bindPhone(VerifyCodeVO verifyCodeVO) throws RuntimeException {
        //先判断用户是否已经绑定了手机,如果已经绑定了手机,抛出异常
        Userinfo userinfo = this.getCurrent();
        if(userinfo!=null && userinfo.isBindPhone()){      //如果用户已经绑定了手机
            throw new BidException("您已绑定手机,请勿重复绑定");
        }

        //调用IVerifyCodeService业务,校验比对Redis.
        verifyService.verifyCode(verifyCodeVO); //期间如果验证失败会有异常抛出,后面就不会继续执行

        //如果验证码正确,执行认证手机逻辑
        log.info("【UserinfoServiceImpl:bindPhone】校验验证码成功,IVerifyCodeService的校验验证码方法没有抛出异常");
        userinfo.addState(BitStatesUtils.OP_BIND_PHONE);
        userinfo.setPhoneNumber(verifyCodeVO.getPhoneNumber());
        this.update(userinfo);
        log.info("【UserinfoServiceImpl:bindPhone】绑定成功! Userinfo更新成功,乐观锁未抛出异常");
    }

    /** 查看一个手机号是否已经用于认证,如果已经用于认证返回true.没有认证过返回false*/
    @Override
    public boolean isPhoneNumberBound(String phoneNumber) {
        Userinfo userinfo = userinfoMapper.selectByPhoneNumber(phoneNumber);
        if(userinfo!=null){
            return true;
        }else{
            return false;
        }
    }

    /** 查看一个邮箱是否已经用于认证,如果已经用于认证返回true.没有认证过返回false*/
    @Override
    public boolean isEmailBound(String email) {
        //判断这个邮箱是否已经被绑定了,如果有就抛异常
        Userinfo userinfo = userinfoMapper.selectByEmail(email);
        if(userinfo!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void bindEmail(String key) throws RuntimeException {

        //调用IVerifyService去Redis做验证
        VerifyEmailVO verifyEmailVO = verifyService.verifyEmail(key);   //如果Redis找不到会抛出异常

        //能到这里说明验证通过,没有抛异常,verifyEmailVO一定不为null.

        //先判断用户是不是没有绑定邮箱,如果已经绑定就抛异常
        Userinfo userinfo = this.get(verifyEmailVO.getUserinfoId());
        if(userinfo.isBindEmail()){
            throw new RuntimeException("该账号已绑定邮箱,请勿重复操作");
        }

        //执行绑定邮箱操作: 修改用户bitState,给email字段填值
        userinfo.addState(BitStatesUtils.OP_BIND_EMAIL);
        userinfo.setEmail(verifyEmailVO.getEmail());
        this.update(userinfo);
        log.info("【UserinfoServiceImpl:bindEmail】绑定成功! id:{},email:{}",verifyEmailVO.getUserinfoId(),verifyEmailVO.getEmail());

    }

    /**更新用户的基本信息*/
    @Override
    public void updateBasicInfo(Userinfo form) {
        SystemDictionaryItem educationBackground = form.getEducationBackground();
        SystemDictionaryItem incomeGrade = form.getIncomeGrade();
        SystemDictionaryItem marriage = form.getMarriage();
        SystemDictionaryItem kidCount = form.getKidCount();
        SystemDictionaryItem houseCondition = form.getHouseCondition();

        Userinfo current = this.getCurrent();
        current.setEducationBackground(educationBackground);
        current.setIncomeGrade(incomeGrade);
        current.setMarriage(marriage);
        current.setKidCount(kidCount);
        current.setHouseCondition(houseCondition);

        if(educationBackground!=null && incomeGrade!=null && marriage!=null && kidCount!=null && houseCondition!=null){
            if(!current.isBasicInfo()){
                current.addState(BitStatesUtils.OP_BASIC_INFO);
            }
        }else{      //如果有其中一个基本资料未填写, 设置bitState
            if(current.isBasicInfo()){
                current.removeState(BitStatesUtils.OP_BASIC_INFO);
            }
        }

        this.update(current);
    }

}
