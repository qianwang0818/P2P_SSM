package com.xmg.p2p.business.qo;

import com.xmg.p2p.base.query.QueryObject;
import lombok.Data;

/**
 * 借款对象的分页条件查询对象
 * @Author: Squalo
 * @Date: 2018/2/28 - 16:53     day08_03
 */
@Data
public class BidRequestQueryObject extends QueryObject {
    private int bidRequestState = -1;       //借款状态,默认全部
}
