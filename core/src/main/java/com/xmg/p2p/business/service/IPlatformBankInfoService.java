package com.xmg.p2p.business.service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

/**
 * 平台的银行账户 服务层接口
 * @Author: Squalo
 * @Date: 2018/3/1 - 19:51      day09_02
 */
public interface IPlatformBankInfoService {

    PageResult query(PlatformBankInfoQueryObject qo);

    void saveOrUpdate(PlatformBankInfo platformBankInfo);

    List<PlatformBankInfo> selectAll();
}
