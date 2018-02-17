package com.xmg.p2p.base.query;

import com.xmg.p2p.base.constant.CommonConstant;
import lombok.Data;

/**
 * 分页查询的条件对象的共同代码封装,抽象父类
 * @Author: Squalo
 * @Date: 2018/2/17 - 15:10     day03_10
 */
@Data
abstract public class QueryObject {
    private Integer currentPage = 1 ;
    private Integer pageSize = CommonConstant.PAGESIZE;

    public int getStart(){
        return (currentPage - 1 ) * pageSize ;
    }
}
