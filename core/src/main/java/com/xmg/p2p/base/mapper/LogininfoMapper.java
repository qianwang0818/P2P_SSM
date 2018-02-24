package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LogininfoMapper {

    int insert(Logininfo record);

    Logininfo selectByPrimaryKey(Long id);

    List<Logininfo> selectAll();

    int updateByPrimaryKey(Logininfo record);

    /**根据用户名查询用户数量*/
    int getCountByUsername(String username);

    Logininfo login(@Param("username") String username, @Param("password") String password, @Param("userType") int userType);

    /**根据用户类型(前台或后台)查询用户类型数量*/
    int getCountByUserType(int userType);

    /**用于用户的用户名自动补全,返回一个List,里面的Map有两个字段:{id:id,username:username}*/
    List<Map<String,Object>> autoComplete(@Param("keyword") String keyword, @Param("userType") int userType);
}