package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/***
 * day03_04
 */
public interface SystemDictionaryMapper {

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    /**高级查询总记录数*/
    int queryForCount(SystemDictionaryQueryObject systemDictionaryQO);

    List<SystemDictionary> query(SystemDictionaryQueryObject systemDictionaryQO);

    int updateByPrimaryKey(SystemDictionary record);

}