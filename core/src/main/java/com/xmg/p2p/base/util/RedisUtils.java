package com.xmg.p2p.base.util;

import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 与Redis相关的工具包
 * @Author: Squalo
 * @Date: 2018/2/19 - 11:01
 */

public class RedisUtils {

    private static final String REDIS_VERIFYCODE_PREFIX = "verifyCode_";
    private static final String REDIS_SENTTIMES_PREFIX = "sentTimes_";

    /**得到当前手机号在24h内的发送信息数量*/
    public static int getSentTimes(RedisTemplate<String,String> redisTemplate, String phoneNumber){
        String sentTimes = redisTemplate.opsForValue().get(REDIS_SENTTIMES_PREFIX + phoneNumber);
        if(sentTimes==null){                                //24h内,本手机号没有发送过验证码
            return 0 ;
        }else {
            return Integer.parseInt(sentTimes);
        }
    }

    /**从Redis得到当前的手机短信验证码VerifyCodeVO对象*/
    public static VerifyCodeVO getCurrentVerifyCode(RedisTemplate<String,String> redisTemplate, String phoneNumber) {
        String verifyCodeAndLastSendTime = redisTemplate.opsForValue().get(REDIS_VERIFYCODE_PREFIX+phoneNumber);
        if(verifyCodeAndLastSendTime==null){                //如果之前的验证码已经过五分钟失效或未曾发过验证码.
            return null;
        }
        String[] split = verifyCodeAndLastSendTime.split("#");      // '验证码#上次发送时间'
        Date lastSendTime = DateUtil.getDateFromString(split[1], "yyyy-MM-dd HH:mm:ss");
        return new VerifyCodeVO(split[0], phoneNumber, lastSendTime);
    }

    /**将手机验证码VO放入Redis*/
    public static void putVerifyCode(RedisTemplate<String,String> redisTemplate, VerifyCodeVO verifyCodeVO) {
        String verifyCode = verifyCodeVO.getVerifyCode();
        String phoneNumber = verifyCodeVO.getPhoneNumber();
        String lastSendTime = DateUtil.getStringFromDate(verifyCodeVO.getLastSendTime(), "yyyy-MM-dd HH:mm:ss");
        //往Redis里面存一对K-V: '手机号'-'验证码#上次发送时间'   有效期5分钟
        redisTemplate.opsForValue().set(REDIS_VERIFYCODE_PREFIX+phoneNumber,verifyCode+"#"+lastSendTime, BidConst.VERIFYCODE_VALIDATE_SECOND, TimeUnit.SECONDS);

        //记录24h内该手机号的发送次数
        String sentTimesString = redisTemplate.opsForValue().get(REDIS_SENTTIMES_PREFIX + phoneNumber);
        int sentTimes ;
        if(sentTimesString==null){      //24h内没有发送过短信,那么发送次数就是零
            sentTimes = 0;
        }else{                          //24h内有发送过短信,那么发送次数从Redis中取出
            sentTimes = Integer.parseInt(sentTimesString);
        }
        sentTimes++;                    //发送次数加一
        //把发送次数存进Redis中,从第五次发送短信开始计时24h不让发送.
        redisTemplate.opsForValue().set(REDIS_SENTTIMES_PREFIX+phoneNumber, sentTimes+"", 1, TimeUnit.DAYS);
    }
}
