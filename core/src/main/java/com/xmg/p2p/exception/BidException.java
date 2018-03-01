package com.xmg.p2p.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 * @Author: Squalo
 * @Date: 2018/3/1 - 16:43
 */
@Data
@NoArgsConstructor
public class BidException extends RuntimeException {

    private String url;

    public BidException(String message) {
        super(message);
    }

    public BidException(String message, String url) {
        super(message);
        this.url = url;
    }



}
