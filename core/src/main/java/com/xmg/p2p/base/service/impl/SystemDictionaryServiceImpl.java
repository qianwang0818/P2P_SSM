package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryItemMapper;
import com.xmg.p2p.base.mapper.SystemDictionaryMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典相关服务 实现类
 * @Author: Squalo
 * @Date: 2018/2/20 - 23:08     day05_06
 */
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public PageResult queryDictionary(SystemDictionaryQueryObject qo) {
        int totalCount = systemDictionaryMapper.queryForCount(qo);
        if(totalCount>0){
            List<SystemDictionary> list = systemDictionaryMapper.query(qo);
            return new PageResult(list, totalCount, qo.getCurrentPage(), qo.getPageSize());
        }else{
            return PageResult.empty(qo.getPageSize());
        }
    }

    /**修改或者保存数据字典分类*/
    @Override
    public void saveOrUpdateDictionary(SystemDictionary systemDictionary) {
        if(systemDictionary.getId()==null){     //没有Id,添加操作
            systemDictionaryMapper.insert(systemDictionary);
        }else{                                  //有id,修改操作
            systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
        }
    }

    @Override
    public List<SystemDictionary> selectAllSystemDictionary() {
        return systemDictionaryMapper.selectAll();
    }

    /**根据数据字典分类sn 查询明细*/
    @Override
    public List<SystemDictionaryItem> selectItemByParentSn(String sn) {
        return systemDictionaryItemMapper.selectByParentSn(sn);
    }

    /**分页查询数据字典明细*/
    @Override
    public PageResult queryDictionaryItem(SystemDictionaryQueryObject qo) {
        int totalCount = systemDictionaryItemMapper.queryForCount(qo);
        if(totalCount>0){
            List<SystemDictionaryItem> list = systemDictionaryItemMapper.query(qo);
            return new PageResult(list, totalCount, qo.getCurrentPage(), qo.getPageSize());
        }else{
            return PageResult.empty(qo.getPageSize());
        }
    }

    /**修改或者保存数据字典明细*/
    @Override
    public void saveOrUpdateDictionaryItem(SystemDictionaryItem systemDictionaryItem) {
        if(systemDictionaryItem.getId()==null){     //没有Id,添加操作
            systemDictionaryItemMapper.insert(systemDictionaryItem);
        }else{                                      //有id,修改操作
            systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
        }
    }

}
