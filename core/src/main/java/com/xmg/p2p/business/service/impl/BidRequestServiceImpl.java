package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.mapper.BidRequestMapper;
import com.xmg.p2p.business.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**借款对象相关服务接口
 * @Author: Squalo
 * @Date: 2018/2/27 - 18:39     day08_01
 */
@Service
public class BidRequestServiceImpl implements IBidRequestService {

    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Override
    public void update(BidRequest bidRequest) {
        int ret = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (ret==0){
            throw new RuntimeException("乐观锁失败,bidRequest.id:" + bidRequest.getId());
        }
    }
}
