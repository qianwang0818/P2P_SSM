package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Userinfo;
import java.util.List;
import java.util.Map;

/***
 * day03_04
 */
public interface UserinfoMapper {

    int insert(Userinfo record);

    Userinfo selectByPrimaryKey(Long id);

    List<Userinfo> selectAll();

    int updateByPrimaryKey(Userinfo record);

    Userinfo selectByPhoneNumber(String phoneNumber);

    Userinfo selectByEmail(String email);

}