package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseAuditDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 借款审核历史对象
 * @Author: Squalo
 * @Date: 2018/2/28 - 16:36     day08_03
 */
@Data
@NoArgsConstructor
public class BidRequestAuditHistory extends BaseAuditDomain {

    public static final int PUBLISH_AUDIT = 0;      //发标前审核
    public static final int FULL_AUDIT_1 = 1;       //满标一审
    public static final int FULL_AUDIT_2 = 2;       //满标二审

    private Long bidRequestId;
    private int auditType;


    public BidRequestAuditHistory(Long bidRequestId, Logininfo applier, Date applyTime, Logininfo auditor, Date auditTime, int auditType, String remark, int state) {
        this.bidRequestId = bidRequestId;
        this.applier = applier;
        this.applyTime = applyTime;
        this.auditor = auditor;
        this.auditTime = auditTime;
        this.auditType = auditType;
        this.remark = remark;
        this.state = state;
    }
}
