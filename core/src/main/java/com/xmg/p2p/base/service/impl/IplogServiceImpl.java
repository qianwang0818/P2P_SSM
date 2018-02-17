package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.mapper.IplogMapper;
import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Squalo
 * @Date: 2018/2/17 - 15:31
 */
@Service
public class IplogServiceImpl implements IIplogService {

    @Autowired
    private IplogMapper iplogMapper;

    @Override
    public PageResult query(IplogQueryObject iplogQO) {
        int count = iplogMapper.queryForCount(iplogQO);
        if(count>0){    //有数据
            List<Iplog> list = iplogMapper.query(iplogQO);
            return new PageResult(list,count,iplogQO.getCurrentPage(),iplogQO.getPageSize());
        }
        return PageResult.empty(iplogQO.getPageSize());
    }
}
