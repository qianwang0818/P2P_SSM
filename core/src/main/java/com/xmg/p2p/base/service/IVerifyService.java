package com.xmg.p2p.base.service;

import com.xmg.p2p.base.vo.VerifyCodeVO;
import com.xmg.p2p.base.vo.VerifyEmailVO;

/**
 * 手机验证码/邮箱认证相关服务
 * @Author: Squalo
 * @Date: 2018/2/18 - 18:10     day04_07
 */
public interface IVerifyService {

    /**给指定手机号发送验证码*/
    void sendVerifyCode(String phoneNumber) throws RuntimeException;

    /**用于验证手机验证码,比对Redis*/
    void verifyCode(VerifyCodeVO verifyCodeVO) throws RuntimeException;

    /**发送认证邮件,并保存到Redis*/
    void sendVerifyEmail(String email) throws RuntimeException;

    /**验证认证邮件的key值,比对Redis*/
    VerifyEmailVO verifyEmail(String key) throws RuntimeException;
}
