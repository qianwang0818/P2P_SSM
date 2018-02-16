package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Userinfo;

/**
 * 用户相关信息服务
 * @Author: Squalo
 * @Date: 2018/2/16 - 18:16    day03_04
 */
public interface IUserinfoService {

    /**支持乐观锁的更新方法*/
    void update(Userinfo userinfo);
}
