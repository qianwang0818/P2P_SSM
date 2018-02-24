package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.query.VideoAuthQueryObject;

import java.util.List;

/**
 * day06_09
 */
public interface VideoAuthMapper {

    int insert(VideoAuth videoAuth);

    VideoAuth selectByPrimaryKey(Long id);

    /**分页条件查询总记录数*/
    int queryForCount(VideoAuthQueryObject qo);

    /**分页条件查询数据list*/
    List<VideoAuth> query(VideoAuthQueryObject qo);

}