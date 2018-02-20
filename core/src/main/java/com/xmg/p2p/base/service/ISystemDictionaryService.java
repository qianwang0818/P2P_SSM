package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

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
}
