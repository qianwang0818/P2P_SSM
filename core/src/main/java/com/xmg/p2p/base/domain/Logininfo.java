package com.xmg.p2p.base.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Squalo
 * @Date: 2018/2/15 - 17:00
 */

@Data
@NoArgsConstructor
public class Logininfo extends BaseDomain {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOCK = 1;

    public static final int USER_MANAGER = 0;   //后台用户
    public static final int USER_CLIENT = 1;     //前台用户

    private String username;
    private String password;
    private int state;

    private int userType;

    /**有参构造*/
    public Logininfo(String username, String password, int state, int userType) {
        this.username = username;
        this.password = password;
        this.state = state;
        this.userType = userType;
    }
}
