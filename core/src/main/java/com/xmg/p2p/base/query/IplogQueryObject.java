package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 登录日志查询的条件对象
 * @Author: Squalo
 * @Date: 2018/2/17 - 15:12     day03_10
 */
@Data
public class IplogQueryObject extends QueryObject{

    private Date beginDate;     //查询日志的起始日期
    private Date endDate;       //查询日志的结束日期
    private int state = -1;     //查询登录状态    默认值: -1 表示查询全部(包括登录成功/登录失败)
    private String username;    //根据用户名查询登录日志

    /**
     * 因为IplogQueryObject里面的参数都是直接让SpringMVC注入进来的
     * 如果没有配置日期的格式,则没法注入
     * 所以配置注解告诉SpringMVC日期的注入格式
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {  //对用户输入的查询日期字符串进行格式化注入
        this.beginDate = beginDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {      //对用户输入的查询日期字符串进行格式化注入
        this.endDate = endDate;
    }

    /**结束日期,结算到该日期的23时59分59秒*/
    public Date getEndDate() {
        return endDate==null ? null : DateUtil.endOfDay(endDate);
    }

    /**如果用户名是空字符串,将空字符串置为null*/
    public String getUsername() {
        return StringUtils.isEmpty(username) ? null : username;
    }
}
