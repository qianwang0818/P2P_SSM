package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.query.IplogQueryObject;
import java.util.List;

/***
 * day03_09
 */
public interface IplogMapper {

    int insert(Iplog iplog);

    /**高级查询总记录数*/
    int queryForCount(IplogQueryObject iplogQO);

    /**查询当前页的数据*/
    List<Iplog> query(IplogQueryObject iplogQO);
}