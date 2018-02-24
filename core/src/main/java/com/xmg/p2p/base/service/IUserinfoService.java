package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.vo.VerifyCodeVO;

import java.util.List;
import java.util.Map;

/**
 * 用户相关信息服务
 * @Author: Squalo
 * @Date: 2018/2/16 - 18:16    day03_04
 */
public interface IUserinfoService {

    /**支持乐观锁的更新方法*/
    void update(Userinfo userinfo);

    void add(Userinfo userinfo);

    Userinfo get(Long id);

    /**得到当前Session中的Logininfo对应的Userinfo*/
    Userinfo getCurrent();

    /**用户手机认证,其中包括校验Redis验证码逻辑*/
    void bindPhone(VerifyCodeVO verifyCodeVO) throws RuntimeException;

    boolean isPhoneNumberBound(String phoneNumber);

    boolean isEmailBound(String email);

    /**点击认证邮件链接,执行邮箱认证*/
    void bindEmail(String key) throws RuntimeException;

    /**更新用户的基本信息*/
    void updateBasicInfo(Userinfo userinfo);

}
