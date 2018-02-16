package com.xmg.p2p.base.domain;

import lombok.Data;

/**
 * @Author: Squalo
 * @Date: 2018/2/15 - 17:00
 */

@Data
public class Logininfo extends BaseDomain {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_LOCK = 1;

    private String username;
    private String password;
    private int state;
}
