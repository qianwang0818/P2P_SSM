package com.xmg.p2p.base.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 邮箱验证邮件用的VO
 * @Author: Squalo
 * @Date: 2018/2/20 - 11:51
 */
@Data
@NoArgsConstructor
public class VerifyEmailVO {
    private String key;             //生成的密钥,作为Redis的Key
    private Long userinfoId;       //Userinfo表的主键Id
    private String email;           //填写的邮箱

    public VerifyEmailVO(String key, Long userinfoId, String email) {
        this.key = key;
        this.userinfoId = userinfoId;
        this.email = email;
    }
}
