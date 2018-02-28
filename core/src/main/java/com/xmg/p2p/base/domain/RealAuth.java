package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.config.MyConfig;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 * @Author: Squalo
 * @Date: 2018/2/23 - 10:40     day06_02
 */
@Data
public class RealAuth extends BaseAuditDomain {

    public static final int SEX_FEMALE = 0;
    public static final int SEX_MALE = 1;

    private String realName;
    private int sex;
    private String idNumber;
    private String birthDate;
    private String address;     //证件地址
    private String image1;      //身份证正面照片地址
    private String image2;      //身份证反面照片地址


    public String getSexDisplay() {
        if(this.sex == SEX_MALE){
            return "男";
        }else if(this.sex == SEX_FEMALE){
            return "女";
        }else{
            return "妖";
        }
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("applier",applier.getUsername());
        map.put("realName",realName);
        map.put("idNumber",idNumber);
        map.put("sex",getSexDisplay());
        map.put("birthDate",birthDate);
        map.put("address",address);
        String prefix = MyConfig.websiteHost;
        map.put("image1",prefix + image1);
        map.put("image2",prefix + image2);
        return JSONObject.toJSONString(map);
    }

    /**获取用户真实名字的隐藏字符串，只显示姓氏*/
    public String getAnonymousRealName() {
        if (StringUtils.hasLength(this.realName)) {
            int len = realName.length();
            String replace = "";
            replace += realName.charAt(0);
            for (int i = 1; i < len; i++) {
                replace += "*";
            }
            return replace;
        }
        return realName;
    }

    /**获取用户身份号码的隐藏字符串*/
    public String getAnonymousIdNumber() {
        if (StringUtils.hasLength(idNumber)) {
            int len = idNumber.length();
            String replace = "";
            for (int i = 0; i < len; i++) {
                if ((i > 5 && i < 10) || (i > len - 5)) {
                    replace += "*";
                } else {
                    replace += idNumber.charAt(i);
                }
            }
            return replace;
        }
        return idNumber;
    }

    /**获取用户住址的隐藏字符串*/
    public String getAnonymousAddress() {
        if (StringUtils.hasLength(address) && address.length() > 4) {
            String last = address.substring(address.length() - 4,
                    address.length());
            String stars = "";
            for (int i = 0; i < address.length() - 4; i++) {
                stars += "*";
            }
            return stars + last;
        }
        return address;
    }

}
