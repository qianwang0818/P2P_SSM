package com.xmg.p2p.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用于读取配置信息的配置信息对象
 * @Author: Squalo
 * @Date: 2018/2/24 - 21:26     自行优化
 */
@Component
public class MyConfig {

    public static String websiteHost;

    @Value("${host.websiteHost}")
    public void setWebsiteHost(String websiteHost) {
        MyConfig.websiteHost = websiteHost;
    }
}
