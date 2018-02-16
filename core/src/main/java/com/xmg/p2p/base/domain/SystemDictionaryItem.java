package com.xmg.p2p.base.domain;

import lombok.Data;

/**
 * 数据字典明细
 * @Author: Squalo
 * @Date: 2018/2/16 - 16:42    day03_03
 */

@Data
public class SystemDictionaryItem extends BaseDomain {

    private Long parentId;
    private String title;
    private int sequence;

}
