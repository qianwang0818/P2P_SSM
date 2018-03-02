package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.base.domain.BaseAuditDomain;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线下充值单
 * @Author: Squalo
 * @Date: 2018/3/1 - 21:17      day09_03
 */
@Data
public class RechargeOffline extends BaseAuditDomain {
    private PlatformBankInfo bankInfo;  //平台账户对象
    private String tradeCode;           //交易号
    private BigDecimal amount;          //充值金额
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date tradeTime;           //交易号
    private String note;                //充值说明

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("applier",applier.getUsername());
        map.put("bankInfo",bankInfo);
        map.put("tradeCode",tradeCode);
        map.put("amount",amount);
        map.put("tradeTime",new SimpleDateFormat("yyyy-MM-dd").format(tradeTime));
        return JSONObject.toJSONString(map);
    }
}
