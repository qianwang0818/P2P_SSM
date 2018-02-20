package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IMailService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVerifyService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.RedisUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import com.xmg.p2p.base.vo.VerifyEmailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 手机验证码/邮箱认证相关服务实现类
 * @Author: Squalo
 * @Date: 2018/2/18 - 18:11     day04_07
 */
@Service
@Slf4j
public class VerifyServiceImpl implements IVerifyService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IMailService mailService;

    @Value("${mail.hostUrl}")
    private String hostUrl;

    @Override
    public void sendVerifyCode(String phoneNumber) throws RuntimeException {
        //判断这个手机号是否已经被认证过
        if(userinfoService.isPhoneNumberBound(phoneNumber)){    //返回true说明已经这个手机号认证过了
            throw new RuntimeException("此手机号已绑定了账户,请先解除绑定.");
        }

        //判断24h内短信配额5条是否已用完
        if( RedisUtils.getSentTimes(phoneNumber) >= 5){
            throw new RuntimeException("同一手机号24小时内只能接收5条短信,请明天再试");
        }

        VerifyCodeVO vo = RedisUtils.getVerifyCode(phoneNumber);     //从Redis中获取最后一次成功发送验证码的相关信息
        Date currentTime = new Date();                                                      //获得当前时间,后面多处使用
        if(vo==null || DateUtil.secondsBetween(vo.getLastSendTime(), currentTime) >= BidConst.VERIFYCODE_SENDGAP_SECOND){     //还未发送过验证码或距离上次发送超过90秒,满足发送要求
            //生成一个验证码
            String verifyCode = RandomStringUtils.randomNumeric(4);
            //发送短信
            log.info("【VerifyServiceImpl:sendVerifyCode】验证码:{},手机号:{},发送时间:{}",verifyCode,phoneNumber, currentTime);
            //把手机号码,验证码,发送时间 装配到VO中并保存到Session
            RedisUtils.putVerifyCode(new VerifyCodeVO(verifyCode, phoneNumber, currentTime));
        }else{      //不满足发送间隔
            throw new RuntimeException("发送短信过于频繁,请稍后再试");     //TODO 自定义异常
        }
    }
    //视频day04_07原本发送验证码方法,存Session有缺陷: 1.重启浏览器可刷新Session  2.更换绑定手机号依然要等90秒重发短信
    /*public void sendVerifyCode(String phoneNumber) throws RuntimeException {
        //判断当前是否能够发送短信
        VerifyCodeVO vo = UserContext.getVerifyCode();   //从Session中获取最后一次成功发送验证码的相关信息
        Date currentTime = new Date();                          //获得当前时间,后面多处使用
        if(vo==null || DateUtil.secondsBetween(vo.getLastSendTime(), currentTime)>90L){     //还未发送过验证码或距离上次发送超过90秒,满足发送要求
            //生成一个验证码
            String verifyCode = RandomStringUtils.randomNumeric(4);
            //发送短信
            log.info("【VerifyServiceImpl:sendVerifyCode】验证码:{},手机号:{},发送时间:{}",verifyCode,phoneNumber, currentTime);
            //把手机号码,验证码,发送时间 装配到VO中并保存到Session
            UserContext.putVerifyCode(new VerifyCodeVO(verifyCode, phoneNumber, currentTime));
        }else{      //不满足发送间隔
            throw new RuntimeException("发送短信过于频繁,请稍后再试");     //TODO 自定义异常
        }
    }*/

    /**用于验证手机验证码,比对Redis*/
    @Override
    public void verifyCode(VerifyCodeVO verifyCodeVO) throws RuntimeException {
        VerifyCodeVO redisVO = RedisUtils.getVerifyCode(verifyCodeVO.getPhoneNumber());
        if(redisVO==null){                                              //根据手机号在Redis找不到验证码,抛异常
            throw new RuntimeException("验证码已过期或不存在,请重新发送手机验证码");
        }
        if( ! redisVO.getVerifyCode().equalsIgnoreCase(verifyCodeVO.getVerifyCode()) ){    //能从Redis拿到验证码,但是验证不通过
            throw new RuntimeException("验证码不正确,请检查并重试");
        }
        RedisUtils.invalidateVerifyCode(verifyCodeVO.getPhoneNumber());
    }

    /**发送认证邮件,并保存到Redis*/
    @Override
    public void sendVerifyEmail(String email) throws RuntimeException {
        //先检查当前用户是否已经绑定了邮箱,
        Userinfo current = userinfoService.getCurrent();
        if(current.isBindEmail()){  //如果已经有绑定邮箱,就抛异常
            throw new RuntimeException("您已绑定邮箱,请勿重复绑定.");
        }

        //再检查这个邮箱是否已经用于绑定
        if(userinfoService.isEmailBound(email)){    //返回true说明已经这个邮箱认证过了
            throw new RuntimeException("此邮箱已绑定了账户,请先解除绑定.");
        }

        //构造一封认证邮件
        String key = UUID.randomUUID().toString();
        String href = hostUrl + "/bindEmail.do?key=" + key;
        StringBuilder content = new StringBuilder(100).append("【Eloan屁2屁】请点击")
                .append("<a href='").append(href).append("'>本链接</a>")
                .append("完成认证,有效期为").append(BidConst.VERIFYEMAIL_VALIDATE_DAY).append("天.<br/>")
                .append("若点击无效请复制并打开如下网址<br/>").append(href);

        //发送认证邮件
        try {
            mailService.sendMail(email, "Eloan平台邮箱认证", content.toString());
        } catch (Exception e) {
            log.error("【VerifyServiceImpl:sendVerifyEmail】认证邮件发送失败! 邮箱地址:{},错误信息:{}",email,e.getMessage());
            throw new RuntimeException("认证邮件发送失败");       //TODO 抛出自定义异常
        }

        //构造一个邮箱认证VO对象,并保存到Redis
        VerifyEmailVO verifyEmailVO = new VerifyEmailVO(key, UserContext.getCurrent().getId(), email);
        RedisUtils.putVerifyEmail(verifyEmailVO);
    }

    /**验证认证邮件的key值,比对Redis*/
    @Override
    public VerifyEmailVO verifyEmail(String key) throws RuntimeException {
        VerifyEmailVO redisVO = RedisUtils.getVerifyEmail(key);
        if(redisVO == null){  //如果在Redis中找不到邮箱认证VO,就抛异常
            throw new RuntimeException("认证邮件已过期失效("+BidConst.VERIFYEMAIL_VALIDATE_DAY+"天有效),请重新发送认证邮件");
        }
        return redisVO;
    }

}
