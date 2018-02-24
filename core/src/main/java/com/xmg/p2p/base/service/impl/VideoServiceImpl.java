package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.RealAuth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.domain.VideoAuth;
import com.xmg.p2p.base.mapper.VideoAuthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VideoAuthQueryObject;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVideoService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 视频认证服务 实现类
 * @Author: Squalo
 * @Date: 2018/2/24 - 18:50     day06_10
 */
@Data
@Service
public class VideoServiceImpl implements IVideoService{

    @Autowired
    private VideoAuthMapper videoAuthMapper;

    @Autowired
    private IUserinfoService userinfoService;

    @Override
    public PageResult query(VideoAuthQueryObject qo) {
        int totalCount = videoAuthMapper.queryForCount(qo);
        if(totalCount<=0){
            return PageResult.empty(qo.getPageSize());
        }else{
            List<VideoAuth> list = videoAuthMapper.query(qo);
            return new PageResult(list,totalCount,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    @Override
    public void audit(VideoAuth form) {
        Long applierId = form.getId();
        Userinfo applierUserinfo = userinfoService.get(applierId);
        //判断用户是否存在,不存在就抛异常
        if(applierUserinfo==null){
            throw new RuntimeException("该用户不存在!");
        }

        //判断用户没有通过视频认证,如果已通过就抛异常
        if(applierUserinfo.isVideoAuth()){
            throw new RuntimeException("该用户已通过实名认证,请勿重复审核!");
        }

        //添加一个视频认证对象,设置相关属性
        VideoAuth videoAuth = new VideoAuth();
        Logininfo applier = new Logininfo();
        applier.setId(applierId);
        videoAuth.setApplier(applier);
        videoAuth.setApplyTime(new Date());
        videoAuth.setAuditor(UserContext.getCurrent());
        videoAuth.setAuditTime(new Date());
        videoAuth.setRemark(form.getRemark());
        int state = form.getState();
        videoAuth.setState(state);
        videoAuthMapper.insert(videoAuth);

        //判断state,如果审核通过,修改userinfo状态码
        if(state == VideoAuth.STATE_AUDIT){
            applierUserinfo.addState(BitStatesUtils.OP_VEDIO_AUTH);
            userinfoService.update(applierUserinfo);
        }
    }
}
