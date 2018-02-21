package com.xmg.p2p.base.query;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 数据字典查询对象
 * @Author: Squalo
 * @Date: 2018/2/20 - 22:47     day05_06
 */
@Data
public class SystemDictionaryQueryObject extends QueryObject {

    private String keyword;

    private Long parentId;

    public String getKeyword(){        //如果是空字符串,就返回null
        return StringUtils.isEmpty(keyword) ? null : keyword ;
    }

}
