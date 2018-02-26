package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
}
