package com.xmg.p2p.base.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 登录状态信息,记录于登录日志
 * @Author: Squalo
 * @Date: 2018/2/17 - 14:19     day03_09
 */
@Data
@NoArgsConstructor
public class Iplog extends BaseDomain{

    public static final int STATE_SUCCESS = 1 ;
    public static final int STATE_FAILED = 0 ;

    private String ip;
    private int state;
    private String username;
    private Date loginTime;

    /**
     * @param ip
     * @param username
     * @param loginTime
     */
    public Iplog(String ip, String username, Date loginTime) {
        this.ip = ip;
        this.username = username;
        this.loginTime = loginTime;
    }

    public String getStateDisplay(){
        if(state == STATE_SUCCESS){
            return "登录成功";
        }else if(state == STATE_FAILED){
            return "登录失败";
        }else {
            return "未知的状态";
        }
    }
}
