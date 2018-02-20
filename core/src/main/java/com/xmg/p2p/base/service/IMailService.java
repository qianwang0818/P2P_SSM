package com.xmg.p2p.base.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * 专门用于发送邮件的服务
 * @Author: Squalo
 * @Date: 2018/2/20 - 18:13     day05_05
 */
public interface IMailService {

    /*
     * 发送邮件
     * @param target    目标邮箱地址
     * @param title     邮件标题
     * @param content   邮件内容
     */
    void sendMail(String target, String title, String content) throws MessagingException, UnsupportedEncodingException;

}
