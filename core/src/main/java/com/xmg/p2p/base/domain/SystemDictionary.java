package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典分类
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:42    day03_03
 */

@Data
public class SystemDictionary extends BaseDomain{

    private String sn;          //数据字典组名

    private String title;       //组名的英文编码

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("sn",sn);
        map.put("title",title);
        return JSONObject.toJSONString(map);
    }

}
