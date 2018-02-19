package com.xmg.p2p.base.service;

import com.xmg.p2p.base.vo.VerifyCodeVO; /**
 * 手机验证码相关服务
 * @Author: Squalo
 * @Date: 2018/2/18 - 18:10     day04_07
 */
public interface IVerifyCodeService {

    /**给指定手机号发送验证码*/
    void sendVerifyCode(String phoneNumber) throws RuntimeException;

    /**用于验证手机验证码,比对Redis*/
    void verifyCode(VerifyCodeVO verifyCodeVO) throws RuntimeException;
}
