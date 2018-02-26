package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典明细
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:42    day03_03
 */

@Data
@NoArgsConstructor
public class SystemDictionaryItem extends BaseDomain {

    private Long parentId;
    private String title;
    private int sequence;

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("parentId",parentId);
        map.put("title",title);
        map.put("sequence",sequence);
        return JSONObject.toJSONString(map);
    }

    public SystemDictionaryItem(Long id) {
        this.id = id;
    }
}
