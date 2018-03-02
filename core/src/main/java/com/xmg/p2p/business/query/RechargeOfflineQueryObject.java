package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.AuditQueryObject;
import lombok.Data;

/**
 * 线下充值分页条件查询对象
 * @Author: Squalo
 * @Date: 2018/3/2 - 13:46      day09_03
 */
@Data
public class RechargeOfflineQueryObject extends AuditQueryObject {
    private Long applierId;
}
