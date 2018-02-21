package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/***
 * day03_04
 */
public interface SystemDictionaryItemMapper {

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    /**高级条件查询,查询总记录条数*/
    int queryForCount(SystemDictionaryQueryObject qo);

    /**高级条件查询,分页查询listData*/
    List<SystemDictionaryItem> query(SystemDictionaryQueryObject qo);

    int updateByPrimaryKey(SystemDictionaryItem record);
}