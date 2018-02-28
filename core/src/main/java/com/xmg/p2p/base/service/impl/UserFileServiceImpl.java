package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * 风控资料服务
 * @Author: Squalo
 * @Date: 2018/2/25 - 20:17     day07_02
 */
@Service
public class UserFileServiceImpl implements IUserFileService {

    @Autowired
    private UserFileMapper userFileMapper;

    @Autowired
    private IUserinfoService userinfoService;

    /**保存风控资料对象*/
    @Override
    public void save(String fileName) {
        UserFile userFile = new UserFile(UserContext.getCurrent(), new Date(), fileName, UserFile.STATE_NORMAL);
        userFileMapper.insert(userFile);
    }

    /**根据是否选择材料类型,列出该用户所有的认证材料list
     * @param hasType 是否已选择风控材料类型
     */
    @Override
    public List<UserFile> listFilesByHasType(Long applierId, boolean hasType) {
        return userFileMapper.selectFilesByApplierIdAndHasType(applierId,hasType);
    }

    @Override
    public void batchUpdateFileType(Long[] ids, Long[] fileTypes) {
        for (int i = 0 ; i < ids.length ; i++ ){
            UserFile userFile = userFileMapper.selectByPrimaryKey(ids[i]);
            userFile.setFileType(new SystemDictionaryItem(fileTypes[i]));
            userFileMapper.updateByPrimaryKey(userFile);
        }
    }

    @Override
    public PageResult query(UserFileQueryObject qo) {
        int totalCount = userFileMapper.queryForCount(qo);
        if(totalCount<=0){
            return PageResult.empty(qo.getPageSize());
        }else{
            List<UserFile> list = userFileMapper.query(qo);
            return new PageResult(list,totalCount,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    @Override
    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return userFileMapper.query(qo);
    }

    /**审核风控材料*/
    @Override
    public void audit(UserFile form) {
        int state = form.getState();        //审核结果(通过or拒绝)
        int score = form.getScore();        //材料得分
        //查询数据库,这个风控材料对象是未审核状态.如果是已审核就抛异常
        UserFile userFile = userFileMapper.selectByPrimaryKey(form.getId());
        if (userFile==null){
            throw new RuntimeException("该风控材料不存在!");
        }
        if(userFile.getState()!=UserFile.STATE_NORMAL){
            throw new RuntimeException("该风控材料已被审核,请刷新页面!");
        }
        //设置通用属性
        userFile.setAuditor(UserContext.getCurrent());
        userFile.setAuditTime(new Date());
        userFile.setState(state);
        userFile.setRemark(form.getRemark());

        //判断审核结果
        if(state == UserFile.STATE_AUDIT){      //如果审核通过,给userinfo风控分加分
            userFile.setScore(score);
            Userinfo userinfo = userinfoService.get(userFile.getApplier().getId());
            userinfo.addScore(score);
            userinfoService.update(userinfo);
        }

        //更新风控材料对象
        userFileMapper.updateByPrimaryKey(userFile);
    }
}
