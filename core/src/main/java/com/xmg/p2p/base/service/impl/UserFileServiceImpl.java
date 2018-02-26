package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
