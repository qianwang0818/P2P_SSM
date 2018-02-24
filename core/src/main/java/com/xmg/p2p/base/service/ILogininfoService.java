package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Logininfo;

import java.util.List;
import java.util.Map;

/**
 * 登陆相关服务
 * @Author: Squalo
 * @Date: 2018/2/15 - 23:04
 */
public interface ILogininfoService {

    /**
     * 用户注册
     * @param username
     * @param password
     */
    void register(String username, String password) throws RuntimeException;

    /**
     * 异步检查用户名是否存在,如果存在返回true,不存在返回false
     * @param username
     * @return
     */
    boolean checkUsername(String username);

    /**
     * 执行用户登陆,如果登陆成功把Logininfo放到域对象,如果失败抛异常
     * @param username
     * @param password
     * @param ip
     * @param userType
     */
    Logininfo login(String username, String password, String ip, int userType);

    /**
     * 初始化超级管理员. 先查询数据库是否有管理员,如果没有就创建并保存
     */
    void initAdmin();


    /**用于用户的用户名自动补全,返回一个List,里面的Map有两个字段:{id:id,username:username}*/
    List<Map<String,Object>> autoComplete(String keyword);
}
