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

    //还款类型
    public final static int RETURN_TYPE_MONTH_INTEREST_PRINCIPAL = 0;   //按月分期,等额本息
    public final static int RETURN_TYPE_MONTH_INTEREST = 1;   //按月到期,先息后本

    //标的类型
    public final static int BIDREQUEST_TYPE_NORMAL = 0;   //普通信用标

    //借款状态
    public final static int BIDREQUEST_STATE_PUBLISH_PENDING = 0;       //待发布
    public final static int BIDREQUEST_STATE_BIDDING = 1;                //招标中
    public final static int BIDREQUEST_STATE_UNDO = 2;                   //已撤销
    public final static int BIDREQUEST_STATE_BIDDING_OVERDUE = 3;       //流标
    public final static int BIDREQUEST_STATE_APPROVE_PENGDING_1 = 4;   //满标一审
    public final static int BIDREQUEST_STATE_APPROVE_PENGDING_2 = 5;   //满标二审
    public final static int BIDREQUEST_STATE_REJECTED = 6;              //满标审核被拒绝
    public final static int BIDREQUEST_STATE_PAYING_BACK = 7;           //还款中
    public final static int BIDREQUEST_STATE_COMPLETE_PAY_BACK = 8;    //已还清
    public final static int BIDREQUEST_STATE_PAY_BACK_OVERDUE = 9;     //逾期
    public final static int BIDREQUEST_STATE_PUBLISH_REFUSE = 10;       //发标审核拒绝

    //系统限制的借款相关额度
    public static final BigDecimal MIN_BID_AMOUNT = new BigDecimal("50.0000");           //系统规定最小投标金额
    public static final BigDecimal MIN_BIDREQUEST_AMOUNT = new BigDecimal("500.0000");  //系统规定最小借款金额
    public static final BigDecimal MIN_CURRENT_RATE = new BigDecimal("5.0000");  //系统规定最小借款利率
    public static final BigDecimal MAX_CURRENT_RATE = new BigDecimal("20.0000");  //系统规定最大借款利率




}
