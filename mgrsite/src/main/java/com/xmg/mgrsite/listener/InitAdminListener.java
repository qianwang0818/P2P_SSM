package com.xmg.mgrsite.listener;

import com.xmg.p2p.base.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化超级管理员账号的监听器,监听Spring容器加载完成
 * 1.在Spring中,实现了ApplicationListener接口的类就可以作为Spring的监听器来监听Spring中的特殊事件
 * 2.实现接口的泛型指定了需要监听的事件. 在Spring中,ApplicationEvent类相当于所有的事件,即监听Spring的所有事件.
 * 3.泛型写ContextRefreshedEvent就可以只监听Spring容器启动完成的事件.
 *
 * @Author: Squalo
 * @Date: 2018/2/18 - 13:37     day04_02
 */
@Component
public class InitAdminListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ILogininfoService logininfoService;
    /**
     * 监听到指定事件的回调函数
     * event就代表本次监听到的事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //调用业务层方法,会先查询数据库是否有管理员,如果没有就创建并保存
        logininfoService.initAdmin();
    }


}
