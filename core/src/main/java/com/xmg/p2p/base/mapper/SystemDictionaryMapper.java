package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionary;

import java.util.List;

/***
 * day03_04
 */
public interface SystemDictionaryMapper {

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

}