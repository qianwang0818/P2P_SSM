package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BitStatesUtils;
import lombok.Data;

/**
 * 用户相关信息
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:40    day03_03
 */

@Data
public class Userinfo extends BaseDomain {
    private int version;    //版本号
    private long bitState = 0 ;  //用户状态码
    private String realName;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private int score = 0;  //风控累计分数

    private SystemDictionaryItem incomeGrade;   //收入
    private SystemDictionaryItem marriage;   //婚姻状况
    private SystemDictionaryItem kidCount;   //子女情况
    private SystemDictionaryItem educationBackground;   //学历
    private SystemDictionaryItem houseCondition;   //住房条件

    public void addState(long state){
        this.bitState = BitStatesUtils.addState(this.bitState, state);
    }

    public void removeState(long state){
        this.bitState = BitStatesUtils.removeState(this.bitState, state);
    }

    public boolean isBindPhone() {
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_PHONE);
    }

    public boolean isBindEmail() {
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BIND_EMAIL);
    }

    public boolean isBasicInfo(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BASIC_INFO);
    }

    public boolean isRealAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_REAL_AUTH);
    }

    public boolean isVideoAuth() {
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_REAL_AUTH);
    }

}
