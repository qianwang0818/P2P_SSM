package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.RedisUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 手机验证码相关服务实现类
 * @Author: Squalo
 * @Date: 2018/2/18 - 18:11     day04_07
 */
@Service
@Slf4j
public class VerifyCodeServiceImpl implements IVerifyCodeService{

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void sendVerifyCode(String phoneNumber) throws RuntimeException {
        //判断24h内短信配额5条是否已用完
        if( RedisUtils.getSentTimes(redisTemplate, phoneNumber) >= 5){
            throw new RuntimeException("同一手机号24小时内只能接收5条短信,请明天再试");
        }

        VerifyCodeVO vo = RedisUtils.getCurrentVerifyCode(redisTemplate, phoneNumber);     //从Redis中获取最后一次成功发送验证码的相关信息
        Date currentTime = new Date();                                                      //获得当前时间,后面多处使用
        if(vo==null || DateUtil.secondsBetween(vo.getLastSendTime(), currentTime) >= BidConst.VERIFYCODE_SENDGAP_SECOND){     //还未发送过验证码或距离上次发送超过90秒,满足发送要求
            //生成一个验证码
            String verifyCode = RandomStringUtils.randomNumeric(4);
            //发送短信
            log.info("【VerifyCodeServiceImpl:sendVerifyCode】验证码:{},手机号:{},发送时间:{}",verifyCode,phoneNumber, currentTime);
            //把手机号码,验证码,发送时间 装配到VO中并保存到Session
            RedisUtils.putVerifyCode(redisTemplate, new VerifyCodeVO(verifyCode, phoneNumber, currentTime));
        }else{      //不满足发送间隔
            throw new RuntimeException("发送短信过于频繁,请稍后再试");     //TODO 自定义异常
        }
    }
    //视频day04_07原本发送验证码方法,存Session有缺陷: 1.重启浏览器可刷新Session  2.更换绑定手机号依然要等90秒重发短信
    /*public void sendVerifyCode(String phoneNumber) throws RuntimeException {
        //判断当前是否能够发送短信
        VerifyCodeVO vo = UserContext.getCurrentVerifyCode();   //从Session中获取最后一次成功发送验证码的相关信息
        Date currentTime = new Date();                          //获得当前时间,后面多处使用
        if(vo==null || DateUtil.secondsBetween(vo.getLastSendTime(), currentTime)>90L){     //还未发送过验证码或距离上次发送超过90秒,满足发送要求
            //生成一个验证码
            String verifyCode = RandomStringUtils.randomNumeric(4);
            //发送短信
            log.info("【VerifyCodeServiceImpl:sendVerifyCode】验证码:{},手机号:{},发送时间:{}",verifyCode,phoneNumber, currentTime);
            //把手机号码,验证码,发送时间 装配到VO中并保存到Session
            UserContext.putVerifyCode(new VerifyCodeVO(verifyCode, phoneNumber, currentTime));
        }else{      //不满足发送间隔
            throw new RuntimeException("发送短信过于频繁,请稍后再试");     //TODO 自定义异常
        }
    }*/

    /**用于验证手机验证码,比对Redis*/
    @Override
    public void verifyCode(VerifyCodeVO verifyCodeVO) throws RuntimeException {
        VerifyCodeVO redisVO = RedisUtils.getCurrentVerifyCode(redisTemplate, verifyCodeVO.getPhoneNumber());
        if(redisVO==null){                                              //根据手机号在Redis找不到验证码,抛异常
            throw new RuntimeException("验证码已过期或不存在,请重新发送手机验证码");
        }
        if( ! redisVO.getVerifyCode().equalsIgnoreCase(verifyCodeVO.getVerifyCode()) ){    //能从Redis拿到验证码,但是验证不通过
            throw new RuntimeException("验证码不正确,请检查并重试");
        }
    }

}
