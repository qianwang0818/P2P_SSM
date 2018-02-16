package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Squalo
 * @Date: 2018/2/15 - 23:09
 */
@Service
public class LogininfoServiceImpl implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Override
    public void register(String username, String password) throws RuntimeException {
        //判断用户名是否已存在
        int count = logininfoMapper.getCountByUsername(username);
        if (count <= 0){    //如果不存在,设置并保存对象
            Logininfo logininfo = new Logininfo();
            logininfo.setUsername(username);
            logininfo.setPassword(MD5.encode(password));
            logininfo.setState(Logininfo.STATE_NORMAL);
            logininfoMapper.insert(logininfo);
        }else {             //如果已经存在,抛异常
            throw new RuntimeException("用户名已存在!");  //TODO 抛自定义异常
        }
    }

    @Override
    public boolean checkUsername(String username) {
        int count = logininfoMapper.getCountByUsername(username);
        return count > 0 ;
    }

    @Override
    public void login(String username, String password) throws RuntimeException {
        Logininfo current = logininfoMapper.login(username,MD5.encode(password));
        if(current != null){    //登陆成功,把用户放到UserContext
            UserContext.putCurrent(current);
        }else{                  //登陆失败,抛异常
            throw new RuntimeException("用户名或密码有误!");    //TODO 抛自定义异常
        }
    }

}
