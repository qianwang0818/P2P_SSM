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
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 登录业务层实现类
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

    /**前台用户注册使用业务层方法,会初始化三个账户*/
    @Override
    public void register(String username, String password) throws RuntimeException {
        //判断用户名是否已存在
        int count = logininfoMapper.getCountByUsername(username);
        if (count <= 0){    //如果不存在,设置并保存对象
            Logininfo logininfo = new Logininfo(username,MD5.encode(password),Logininfo.STATE_NORMAL,Logininfo.USER_CLIENT);
                //logininfo.setUsername(username);
                //logininfo.setPassword(MD5.encode(password));
                //logininfo.setState(Logininfo.STATE_NORMAL);
                //logininfo.setUserType(Logininfo.USER_CLIENT);
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
    public Logininfo login(String username, String password, String ip, int userType){
        Iplog iplog = new Iplog(ip,username,new Date(),userType);
        Logininfo current = logininfoMapper.login(username,MD5.encode(password),userType);
        if(current != null){    //登陆成功,把用户放到UserContext(实质是HttpSession)
            UserContext.putCurrent(current);
            iplog.setState(Iplog.STATE_SUCCESS);
        }else{                  //登陆失败,抛异常
            iplog.setState(Iplog.STATE_FAILED);
        }
        iplogMapper.insert(iplog);
        return current;
    }

    @Override
    public void initAdmin() {
        //先查询数据库是否有管理员
        int count = logininfoMapper.getCountByUserType(Logininfo.USER_MANAGER);
        if(count==0){   //如果没有就创建默认管理员并保存
            Logininfo admin = new Logininfo(BidConst.DEFAULT_ADMIN_USERNAME, MD5.encode(BidConst.DEFAULT_ADMIN_PASSWORD), Logininfo.STATE_NORMAL, Logininfo.USER_MANAGER);
            logininfoMapper.insert(admin);
        }
    }

}
