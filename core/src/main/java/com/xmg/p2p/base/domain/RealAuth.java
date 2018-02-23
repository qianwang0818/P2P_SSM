package com.xmg.p2p.base.domain;

import lombok.Data;

import java.util.Date;

/**
 * 实名认证对象
 * @Author: Squalo
 * @Date: 2018/2/23 - 10:40     day06_02
 */
@Data
public class RealAuth extends BaseDomain {

    public static final int SEX_FEMALE = 0;
    public static final int SEX_MALE = 1;

    public static final int STATE_NORMAL = 0;   //正常,待审核
    public static final int STATE_AUDIT = 1;    //审核通过
    public static final int STATE_REJECT = 2;   //审核拒绝

    private String realName;
    private int sex;
    private String idNumber;
    private String birthDate;
    private String address;     //证件地址
    private String image1;      //身份证正面照片地址
    private String image2;      //身份证反面照片地址

    private Logininfo applier;  //申请人
    private Date applyTime;     //申请时间
    private Logininfo auditor;  //审核人
    private Date auditTime;     //审核时间
    private String remark;      //审核备注
    private int state;          //校验状态

}
