package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.UserFile;

import java.util.List;

/**
 * 风控资料服务
 * @Author: Squalo
 * @Date: 2018/2/25 - 20:17     day07_02
 */
public interface IUserFileService {
    /**保存风控资料对象*/
    void save(String fileName);

    /**根据是否选择材料类型,列出该用户所有的认证材料list
     * @param hasType 是否已选择风控材料类型
     */
    List<UserFile> listFilesByHasType(Long applierId, boolean hasType);

    void batchUpdateFileType(Long[] ids, Long[] fileTypes);


}
