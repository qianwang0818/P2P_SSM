package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.base.domain.BaseDomain;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台银行账号
 * @Author: Squalo
 * @Date: 2018/3/1 - 19:32      day09_02
 */
@Data
public class PlatformBankInfo extends BaseDomain {
    private String bankName;            //银行名称
    private String accountName;         //开户人姓名
    private String accountNumber;       //银行账号
    private String bankForkName;        //开户支行
    private String iconcls;

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("bankName",bankName);
        map.put("accountName",accountName);
        map.put("accountNumber",accountNumber);
        map.put("bankForkName",bankForkName);
        return JSONObject.toJSONString(map);
    }
}
