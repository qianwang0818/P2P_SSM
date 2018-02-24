package com.xmg.p2p.base.query;

import com.xmg.p2p.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 审核认证相关条件查询的 基础条件对象
 * @Author: Squalo
 * @Date: 2018/2/24 - 18:35     day06_09
 */
@Getter
@Setter
abstract public class AuditQueryObject extends QueryObject {

    private int state = -1;     //默认值-1表示全部
    private Date beginDate;
    private Date endDate;

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

}
