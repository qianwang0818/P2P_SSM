package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * 专门用于发送邮件的服务 实现类
 *
 * @Author: Squalo
 * @Date: 2018/2/20 - 18:15     day05_05
 */
@Service
@Slf4j
public class MailServiceImpl implements IMailService {

    @Value("${mail.host}")
    private String host;
    @Value("${mail.senderUsername}")
    private String senderUsername;
    @Value("${mail.senderPassword}")
    private String senderPassword;

    @Override
    public void sendMail(String target, String title, String content) throws MessagingException, UnsupportedEncodingException {
        //创建邮件发送者
        JavaMailSenderImpl sender = new JavaMailSenderImpl();

        //设置SMTP服务器地址
        sender.setHost(host);
        //设置发件方的配置 账号和密码
        sender.setUsername(senderUsername);
        sender.setPassword(senderPassword);

        //设置其它配置项
        Properties prop = new Properties();
        //设为true,表示此发件邮箱是需要让服务器认证的
        prop.put("mail.smtp.auth", "true");
        //设置连接超时时间
        prop.put("mail.smtp.timeout", "25000");
        //将其它配置项装载进邮件发送者sender
        sender.setJavaMailProperties(prop);


        //创建邮件对象
        MimeMessage mimeMessage = sender.createMimeMessage();

        //创建邮箱助手,通过助手帮助设置配置项
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");
        //配置收件邮箱地址
        helper.setTo(target);
        //配置发件方邮箱和昵称 第一个参数是发件人邮箱地址,第二个参数发件人自定义昵称
        helper.setFrom(senderUsername,"屁2屁管理员");
        //设置邮件标题
        helper.setSubject(title);
        //设置邮件内容,第二个参数布尔值:是否html文本
        helper.setText(content, true);


        //使用邮件发送者sender去执行发送邮件对象
        sender.send(mimeMessage);
        log.info("【MailServiceImpl:sendMail】发送成功! 邮箱地址:{},邮件标题:{},邮件内容:{}", target, title, content);

    }


}
