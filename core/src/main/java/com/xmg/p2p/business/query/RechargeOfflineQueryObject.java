package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.AuditQueryObject;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 线下充值分页条件查询对象
 * @Author: Squalo
 * @Date: 2018/3/2 - 13:46      day09_03
 */
@Data
public class RechargeOfflineQueryObject extends AuditQueryObject {

    private Long applierId;         //按线下充值申请人查询
    private Long bankInfoId;        //按开户行查询
    private String tradeCode;       //按银行流水交易号查询

    public String getTradeCode() {      //为空串时返回null
        return StringUtils.isEmpty(this.tradeCode) ? null : this.tradeCode ;
    }
}
