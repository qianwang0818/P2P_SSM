package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.IplogMapper;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Squalo
 * @Date: 2018/2/15 - 23:09
 */
@Service
public class LogininfoServiceImpl implements ILogininfoService {

    @Autowired
    private LogininfoMapper logininfoMapper;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IAccountService accountService  ;

    @Autowired
    private IplogMapper iplogMapper  ;


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
            //初始化Account并保存
            Account account = new Account();
            account.setId(logininfo.getId());
            accountService.add(account);
            //初始化Userinfo并保存
            Userinfo userinfo = new Userinfo();
            userinfo.setId(logininfo.getId());
            userinfoService.add(userinfo);
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
    public Logininfo login(String username, String password, String ip){
        Iplog iplog = new Iplog(ip,username,new Date());
        Logininfo current = logininfoMapper.login(username,MD5.encode(password));
        if(current != null){    //登陆成功,把用户放到UserContext
            UserContext.putCurrent(current);
            iplog.setState(Iplog.STATE_SUCCESS);
        }else{                  //登陆失败,抛异常
            iplog.setState(Iplog.STATE_FAILED);
        }
        iplogMapper.insert(iplog);
        return current;
    }

}
