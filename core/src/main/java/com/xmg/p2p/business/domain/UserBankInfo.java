package com.xmg.p2p.business.domain;

import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import lombok.Data;

/**
 * 用户绑定的银行卡信息
 * @Author: Squalo
 * @Date: 2018/3/5 - 20:37      day11_01
 */
@Data
public class UserBankInfo extends BaseDomain {
    private String bankName;            //银行名称
    private String accountName;         //开户人姓名
    private String accountNumber;       //银行账号
    private String bankForkName;        //开户支行

    private Logininfo logininfo;
}
