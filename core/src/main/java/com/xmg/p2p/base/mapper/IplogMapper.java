package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Iplog;
import java.util.List;

/***
 * day03_09
 */
public interface IplogMapper {

    int insert(Iplog iplog);

    List<Iplog> selectAll();

}