package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.UserFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    List<UserFile> selectAll();

    int updateByPrimaryKey(UserFile record);

    /**根据是否选择材料类型,列出该用户所有的认证材料list
     * @param hasType 是否已选择风控材料类型
     */
    List<UserFile> selectFilesByApplierIdAndHasType(@Param("applierId") Long applierId, @Param("hasType") boolean hasType);

}