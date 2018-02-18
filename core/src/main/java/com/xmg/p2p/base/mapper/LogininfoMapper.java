package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogininfoMapper {

    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    List<Logininfo> selectAll();

    int updateByPrimaryKey(Logininfo record);

    /**
     * 根据用户名查询用户数量
     * @param username
     * @return
     */
    int getCountByUsername(String username);

    Logininfo login(@Param("username") String username, @Param("password") String password, @Param("userType") int userType);

    /**
     * 根据用户类型(前台或后台)查询用户类型数量
     * @param userType
     * @return
     */
    int getCountByUserType(int userType);
}