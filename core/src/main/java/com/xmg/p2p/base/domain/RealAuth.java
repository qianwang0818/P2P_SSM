package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 * @Author: Squalo
 * @Date: 2018/2/23 - 10:40     day06_02
 */
@Data
public class RealAuth extends BaseDomain {

    public static final int SEX_FEMALE = 0;
    public static final int SEX_MALE = 1;

    public static final int STATE_NORMAL = 0;   //正常,待审核
    public static final int STATE_AUDIT = 1;    //审核通过
    public static final int STATE_REJECT = 2;   //审核拒绝

    private String realName;
    private int sex;
    private String idNumber;
    private String birthDate;
    private String address;     //证件地址
    private String image1;      //身份证正面照片地址
    private String image2;      //身份证反面照片地址

    private Logininfo applier;  //申请人
    private Date applyTime;     //申请时间
    private Logininfo auditor;  //审核人
    private Date auditTime;     //审核时间
    private String remark;      //审核备注
    private int state;          //校验状态

    public String getSexDisplay() {
        if(this.sex == SEX_MALE){
            return "男";
        }else if(this.sex == SEX_FEMALE){
            return "女";
        }else{
            return "妖";
        }
    }

    public String getStateDisplay(){
        switch(state){
            case STATE_NORMAL: return "待审核";
            case STATE_AUDIT: return "审核通过";
            case STATE_REJECT: return "审核拒绝";
            default: return "未知的审核状态";
        }
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("applier",this.applier.getUsername());
        map.put("realName",realName);
        map.put("idNumber",idNumber);
        map.put("sex",getSexDisplay());
        map.put("birthDate",birthDate);
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);
        return JSONObject.toJSONString(map);
    }

}
