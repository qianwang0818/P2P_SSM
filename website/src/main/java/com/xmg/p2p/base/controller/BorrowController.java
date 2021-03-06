package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.config.MyConfig;
import com.xmg.p2p.exception.BidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 借款申请相关的控制层
 * @Author: Squalo
 * @Date: 2018/2/20 - 22:01     day05_05
 */
@Controller
@Slf4j
public class BorrowController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IBidRequestService bidRequestService;

    @Autowired
    private IRealAuthService realAuthService;

    @Autowired
    private IUserFileService userFileService;

    /**顶部菜单栏'我要借款'按钮触发,跳转到借款中心*/
    @RequestMapping("borrow")
    public String borrowCenter(Model model){
        Logininfo current = UserContext.getCurrent();
        if(current==null){      //未登录,返回一个静态html页面
            return "redirect:borrow.html";
        }else{                  //已登录,返回FreeMarker的ftl页面
            model.addAttribute("account",accountService.getCurrent());
            model.addAttribute("userinfo",userinfoService.getCurrent());
            model.addAttribute("baseAuthScore", BidConst.BASE_AUTH_SCORE);
            return "borrow";
        }
    }

    /**点击申请借款后跳转到 (成功)信息填写页面 or (拒绝)申请提示信息页面*/
    @RequestMapping("borrow_preapply")
    public String borrowPreapply(Model model){
        try {
            Logininfo current = UserContext.getCurrent();
            if(current==null){
                throw new RuntimeException("您尚未登录,请先执行登录!");
            }
            //先检查用户是否达成借款的前置条件,如果有条件不满足就抛出异常
            bidRequestService.checkBidRequestPrecondition(current.getId());
            model.addAttribute("minBidAmount",BidConst.MIN_BID_AMOUNT);    //系统规定最小投标金额
            model.addAttribute("minBidRequestAmount",BidConst.MIN_BIDREQUEST_AMOUNT);  //系统规定最小借款金额
            model.addAttribute("account",accountService.getCurrent());
            return "borrow_apply";  //没有抛出异常,说明用户具备申请借款的条件
        } catch (RuntimeException e) {
            log.error("【BorrowController:borrowInfo】发生异常:{}",e.getMessage());
            model.addAttribute("msg","申请失败! "+e.getMessage());
            return "borrow_apply_result";
        }
    }

    /**提交借款申请*/
    @RequestMapping("borrow_apply")
    public String borrowApply(BidRequest form , Model model){
        try {
            bidRequestService.apply(form);  //调用借款申请业务,如果有不符合条件的会抛出异常
            //如果没有抛出异常,那么借款对象保存成功
            model.addAttribute("msg","申请成功!" );
        } catch (RuntimeException e) {       //如果捕获到异常,跳转到信息提示页面
            log.error("【BorrowController:borrowApply】发生异常:{}",e.getMessage());
            model.addAttribute("msg","申请失败! "+e.getMessage());
        }
        return "borrow_apply_result";
    }

    /**前端借款标的明细*/
    @RequestMapping("borrow_info")
    public String borrowInfo(Long id, Model model){
        BidRequest bidRequest = bidRequestService.get(id);
        if(bidRequest==null){
            throw new BidException("该借款标的不存在!", "/invest.do");
        }
        int state = bidRequest.getBidRequestState();
        if(state==BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1){
            throw new BidException("该借款标的已满标,请等待审核!","/invest.do");
        }
        if(state!=BidConst.BIDREQUEST_STATE_BIDDING
                && state!=BidConst.BIDREQUEST_STATE_PAYING_BACK && state!=BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK){
            throw new BidException("该借款标的不允许查看!","/invest.do");
        }
        Userinfo applier = userinfoService.get(bidRequest.getCreateUser().getId());
        UserFileQueryObject userFileQueryObject = new UserFileQueryObject(applier.getId(),-1);

        //封装视图数据
        model.addAttribute("bidRequest",bidRequest );
        model.addAttribute("userinfo",applier );
        model.addAttribute("realAuth",realAuthService.get(applier.getRealAuthId()) );
        model.addAttribute("userFiles",userFileService.queryForList(userFileQueryObject) );
        //需要登录方可获取的内容
        Logininfo current = UserContext.getCurrent();
        boolean self = false;  //布尔值,当前用户是否为发标人
        if(current !=null){     //已登录状态
            if(current.getId().equals(applier.getId())){    //当前用户是筹标用户自己
                self = true;
            }else{                                          //当前用户不是筹标用户
                model.addAttribute("account",accountService.getCurrent());
            }
        }
        model.addAttribute("self",self);  //布尔值,当前用户是否为发标人
        return "borrow_info";
    }

}
