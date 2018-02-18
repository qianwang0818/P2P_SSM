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
    private int userType;   //用户登录类型(1-前台/0-后台)

    /**有参构造*/
    public Iplog(String ip, String username, Date loginTime, int userType) {
        this.ip = ip;
        this.username = username;
        this.loginTime = loginTime;
        this.userType = userType;
    }

    /**在FreeMarker中调用*/
    public String getStateDisplay(){
        if(state == STATE_SUCCESS){
            return "登录成功";
        }else if(state == STATE_FAILED){
            return "登录失败";
        }else {
            return "未知的状态";
        }
    }

    /**在FreeMarker中调用*/
    public String getUserTypeDisplay(){
        if(userType == Logininfo.USER_CLIENT){
            return "前台用户";
        }else if(userType == Logininfo.USER_MANAGER){
            return "后台用户";
        }else {
            return "未知的用户";
        }
    }

}
