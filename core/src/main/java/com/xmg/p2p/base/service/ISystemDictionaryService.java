package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

/**
 * 数据字典相关服务
 * @Author: Squalo
 * @Date: 2018/2/20 - 23:06     day05_06
 */
public interface ISystemDictionaryService {

    /**分页查询数据字典分类*/
    PageResult queryDictionary(SystemDictionaryQueryObject systemDictionaryQO);

    /**修改或者保存数据字典分类*/
    void saveOrUpdateDictionary(SystemDictionary systemDictionary);

    /**查询所有数据字典分类List*/
    List<SystemDictionary> selectAllSystemDictionary();

    /**根据数据字典分类sn 查询明细*/
    List<SystemDictionaryItem> selectItemByParentSn(String sn);

    /**分页查询数据字典明细*/
    PageResult queryDictionaryItem(SystemDictionaryQueryObject systemDictionaryQO);

    /**修改或者保存数据字典明细*/
    void saveOrUpdateDictionaryItem(SystemDictionaryItem systemDictionaryItem);

}
