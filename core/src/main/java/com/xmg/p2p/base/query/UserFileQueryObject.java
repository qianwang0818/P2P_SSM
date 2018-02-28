package com.xmg.p2p.base.query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户风控材料查询对象
 * @Author: Squalo
 * @Date: 2018/2/26 - 17:04     day07_03
 */
@Data
@NoArgsConstructor
public class UserFileQueryObject extends AuditQueryObject {

    private Long applierId;

    public UserFileQueryObject(Long applierId, int state, int pageSize) {
        this.applierId = applierId;
        this.setState(state);
        this.setPageSize(pageSize);
    }
}
