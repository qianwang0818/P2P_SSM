package com.xmg.p2p.base.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 存放手机验证码相关内容,这个对象是放在Session/Redis中的
 * @Author: Squalo
 * @Date: 2018/2/19 - 10:30     day04_07
 */
@Data
@NoArgsConstructor
public class VerifyCodeVO {

    private String verifyCode;      //验证码
    private String phoneNumber;     //手机号
    private Date lastSendTime;      //上次成功发送验证码的时间

    public VerifyCodeVO(String verifyCode, String phoneNumber, Date lastSendTime) {
        this.verifyCode = verifyCode;
        this.phoneNumber = phoneNumber;
        this.lastSendTime = lastSendTime;
    }
}
