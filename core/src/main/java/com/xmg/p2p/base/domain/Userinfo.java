package com.xmg.p2p.base.domain;

import lombok.Data;

/**
 * 用户相关信息
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:40    day03_03
 */

@Data
public class Userinfo extends BaseDomain {
    private int version;    //版本号
    private long bitState;  //用户状态码
    private String realName;
    private String idNumber;
    private String phoneNumber;
    private SystemDictionaryItem incomeGrade;   //收入
    private SystemDictionaryItem marriage;   //婚姻状况
    private SystemDictionaryItem kidCount;   //子女情况
    private SystemDictionaryItem educationBackground;   //学历
    private SystemDictionaryItem houseCondition;   //住房条件

}
