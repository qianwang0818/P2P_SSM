package com.xmg.p2p.base.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.config.MyConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 风控材料
 * @Author: Squalo
 * @Date: 2018/2/25 - 19:39     day07_01
 */
@Getter
@Setter
@NoArgsConstructor
public class UserFile extends BaseAuditDomain {

    private String file;   //风控材料图片
    private SystemDictionaryItem fileType;  //风控材料的证件类型
    private int score;      //材料的风控得分

    public UserFile(Logininfo applier, Date applyTime, String file, int state) {
        this.applier = applier;
        this.applyTime = applyTime;
        this.file = file;
        this.state = state;
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("applier",applier.getUsername());
        map.put("fileType",this.fileType.getTitle());
        String prefix = MyConfig.websiteHost;
        map.put("file",prefix + file);
        map.put("score",score);
        map.put("remark",remark);
        return JSONObject.toJSONString(map);
    }
}
