package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionaryItem;

import java.util.List;

/***
 * day03_04
 */
public interface SystemDictionaryItemMapper {

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);
}