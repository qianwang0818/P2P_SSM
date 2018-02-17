package com.xmg.p2p.base.service;

import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;

/**
 * @Author: Squalo
 * @Date: 2018/2/17 - 15:28     day03_10
 */
public interface IIplogService {

    /**分页查询*/
    PageResult query(IplogQueryObject iplogQO);
}
