package com.xmg.p2p.handler;

import com.xmg.p2p.exception.BidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获 自定义异常捕获
 * @Author: Squalo
 * @Date: 2018/3/1 - 17:24
 */
@ControllerAdvice
@Slf4j
public class BidExceptionHandler {

    /**拦截自定义BidException*/
    @ExceptionHandler(value = BidException.class)
    public String handlerBidException(BidException e, Model model){
        String msg = e.getMessage();
        String url = e.getUrl();
        log.error("发送异常! 异常信息:{},跳转url:{}",msg,url);
        model.addAttribute("msg",msg);
        model.addAttribute("url",url);
        return "common_result";
    }

    /**拦截最大异常Exception*/
    @ExceptionHandler(value = Exception.class)
    public String handlerException(Exception e, Model model){
        String msg = e.getMessage();
        String url = "/index.do";
        log.error("发送异常! 异常信息:{},跳转url:{}",msg,url);
        model.addAttribute("msg","操作失败!");
        model.addAttribute("url",url);
        return "common_result";
    }

}
