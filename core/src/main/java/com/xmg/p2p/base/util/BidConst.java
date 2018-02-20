package com.xmg.p2p.base.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 系统中的常量
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:52    day03_03
 */
public class BidConst {

    /**定义存储精度*/
    public static final int STORE_SCALE = 4 ;
    /**定义运算精度*/
    public static final int CAL_SCALE = 8 ;
    /**定义显示精度*/
    public static final int DISPLAY_SCALE = 2 ;

    /**定义系统级别的零*/
    public static final BigDecimal ZERO = new BigDecimal("0.0000");

    /**定义初始授信额度*/
    public static final BigDecimal INIT_BRROW_LIMIT = new BigDecimal("5000.0000");

    /**定义默认管理员的用户名*/
    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    /**定义默认管理员的密码*/
    public static final String DEFAULT_ADMIN_PASSWORD = "123";

    /**手机验证码的重新发送间隔时间*/
    public static final int VERIFYCODE_SENDGAP_SECOND = 90;
    /**手机验证码的有效时间*/
    public static final int VERIFYCODE_VALIDATE_SECOND = 300;
    /**邮箱认证邮件的有效时间*/
    public static final int VERIFYEMAIL_VALIDATE_DAY = 5;

    /**基本风控认证分数,可借款的及格分*/
    public static final int BASE_AUTH_SCORE = 30;

}
