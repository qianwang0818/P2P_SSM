package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;

/**
 * 实名认证对象服务层
 * @Author: Squalo
 * @Date: 2018/2/23 - 12:16     day06_03
 */
public interface IRealAuthService {

    RealAuth get(Long id);

    /**实名认证申请资料提交*/
    void apply(RealAuth realAuth) throws RuntimeException;

    /**分页条件查询实名认证对象*/
    PageResult query(RealAuthQueryObject realAuthQO);

    /**实名认证的审核*/
    void audit(RealAuth form) throws RuntimeException;
}
