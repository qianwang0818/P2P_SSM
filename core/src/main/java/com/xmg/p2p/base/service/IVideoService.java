package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoAuthQueryObject;

/**
 * 视频认证服务
 * @Author: Squalo
 * @Date: 2018/2/24 - 18:49     day06_10
 */
public interface IVideoService {

    PageResult query(VideoAuthQueryObject qo);

    /**增加视频审核记录*/
    void audit(VideoAuth form);
}
