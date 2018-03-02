package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.service.IPlatformBankInfoService;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.mapper.PlatformBankInfoMapper;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**平台的银行账户 服务层接口
 * @Author: Squalo
 * @Date: 2018/3/1 - 19:52      day09_02
 */
@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {

    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    @Override
    public PageResult query(PlatformBankInfoQueryObject qo) {
        int totalCount = platformBankInfoMapper.queryForCount(qo);
        if(totalCount<=0){
            return PageResult.empty(qo.getPageSize());
        }else{
            List<PlatformBankInfo> list = platformBankInfoMapper.query(qo);
            return new PageResult(list,totalCount,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    @Override
    public void saveOrUpdate(PlatformBankInfo platformBankInfo) {
        if(platformBankInfo.getId()==null){     //新增
            platformBankInfoMapper.insert(platformBankInfo);
        }else{                                  //更新
            platformBankInfoMapper.updateByPrimaryKey(platformBankInfo);
        }
    }

    @Override
    public List<PlatformBankInfo> selectAll() {
        return platformBankInfoMapper.selectAll();
    }


}
