package com.xmg.p2p.business.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.mapper.BidRequestAuditHistoryMapper;
import com.xmg.p2p.business.mapper.BidRequestMapper;
import com.xmg.p2p.business.qo.BidRequestQueryObject;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.util.CalculatetUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**借款对象相关服务接口
 * @Author: Squalo
 * @Date: 2018/2/27 - 18:39     day08_01
 */
@Service
public class BidRequestServiceImpl implements IBidRequestService {

    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;


    @Override
    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(BidRequest bidRequest) {
        int ret = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (ret==0){
            throw new RuntimeException("乐观锁失败,bidRequest.id:" + bidRequest.getId());
        }
    }

    /**检查用户是否达成借款的前置条件,如果有条件不满足就抛出异常*/
    @Override
    public void checkBidRequestPrecondition(Long logininfoId) throws RuntimeException {
        //得到用户
        Userinfo ui = userinfoService.get(logininfoId);
        if(ui==null){
            throw new RuntimeException("您尚未登录,请先执行登录!");
        }
        //如果用户目前有借款正在审核中,就抛异常
        if(ui.hasBidRequestProcess()){
            throw new RuntimeException("你目前有标正在申请的流程当中，不能再次发标!");
        }
        //到了这里说明用户在审核中的借款,判断他的认证材料是不是完善,不完善就抛异常
        //需要认证: 基本资料, 实名认证, 风控材料认证, 视频认证, 没有借款在申请流程中
        if ( !(ui.isBasicInfo() && ui.isRealAuth() && ui.getScore()>= BidConst.BASE_AUTH_SCORE && ui.isVideoAuth()) ){
            throw new RuntimeException("你有认证信息未完善，请先完成发标前相关资料认证!");
        }
    }

    @Override
    public void apply(BidRequest form) throws RuntimeException {
        Logininfo logininfo = UserContext.getCurrent();
        if(logininfo==null){
            throw new RuntimeException("您尚未登录,请先执行登录!");
        }

        //先检查用户是否达成借款的前置条件,如果有不满足会抛出异常
        this.checkBidRequestPrecondition(logininfo.getId());

        //判断1 系统最小借款利息≤借款利息≤系统最大借款利息
        BigDecimal currentRate = form.getCurrentRate();
        if( !(currentRate.compareTo(BidConst.MIN_CURRENT_RATE)>=0
                && currentRate.compareTo(BidConst.MAX_CURRENT_RATE)<=0) ) {
            throw new RuntimeException("借款利息小于最小借款利息("+BidConst.MIN_CURRENT_RATE
                    +")或借款利息大于最大借款利息("+BidConst.MAX_CURRENT_RATE+")");
        }

        //判断2 系统最小借款金额≤借款金额≤剩余信用额度
        Account account = accountService.getCurrent();  //拿到申请人的account对象
        BigDecimal bidRequestAmount = form.getBidRequestAmount();
        if( !(bidRequestAmount.compareTo(BidConst.MIN_BIDREQUEST_AMOUNT)>=0
                && bidRequestAmount.compareTo(account.getRemainBorrowLimit())<=0) ) {
            throw new RuntimeException("借款金额小于系统限制最小借款额度("+BidConst.MIN_BIDREQUEST_AMOUNT
                                         +")或借款金额超出您的剩余信用额度("+account.getRemainBorrowLimit()+")");
        }

        //判断3 系统最小投标金额≤用户自定义最小投标金额≤借款金额
        BigDecimal minBidAmount = form.getMinBidAmount();
        if( !(minBidAmount.compareTo(BidConst.MIN_BID_AMOUNT)>=0
                && minBidAmount.compareTo(bidRequestAmount)<=0) ) {
            throw new RuntimeException("最小投标金额小于系统限制最小投标金额("+BidConst.MIN_BID_AMOUNT
                                        +")或最小投标金额大于借款金额("+bidRequestAmount+")");
        }

        //以上都没抛异常,进入借款申请
        //1.创建一个借款对象
        BidRequest bidRequest = new BidRequest();
        //2.拷贝form: 借款金额,借款利率,描述,筹标期限,最小投标金额,还款月数,还款方式,标题
        BeanUtils.copyProperties(form,bidRequest);
        //3.填充审核相关参数
        bidRequest.setApplyTime(new Date());
        bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        bidRequest.setCreateUser(UserContext.getCurrent());
        bidRequest.setTotalRewardAmount(CalculatetUtil.calTotalInterest(
                bidRequest.getReturnType(),bidRequestAmount,currentRate,bidRequest.getMonthes2Return()));
        //4.保存借款对象
        bidRequestMapper.insert(bidRequest);
        //5.给借款人userinfo添加'有标在审中'的状态码,执行更新操作
        Userinfo userinfo = userinfoService.getCurrent();
        userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
        userinfoService.update(userinfo);
    }

    /**分页条件查询待审核借款对象*/
    @Override
    public PageResult query(BidRequestQueryObject qo) {
        int totalCount = bidRequestMapper.queryForCount(qo);
        if(totalCount<=0){
            return PageResult.empty(qo.getPageSize());
        }else{
            List<BidRequest> list = bidRequestMapper.query(qo);
            return new PageResult(list,totalCount,qo.getCurrentPage(),qo.getPageSize());
        }
    }

    /**发标前审核*/
    @Override
    public void publishAudit(BidRequestAuditHistory form) {
        Long id = form.getId();
        int state = form.getState();
        String remark = form.getRemark();
        //查出BidRequest
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);
        if(bidRequest==null){
            throw new RuntimeException("该标的不存在!");
        }
        //判断借款对象处于待审核状态
        if(bidRequest.getBidRequestState()!=BidConst.BIDREQUEST_STATE_PUBLISH_PENDING){
            throw new RuntimeException("该标的已被审核,请勿重复审核!");
        }
        //创建一个审核历史对象,填充数据,保存
        BidRequestAuditHistory history = new BidRequestAuditHistory(id,bidRequest.getCreateUser(),
                bidRequest.getApplyTime(),UserContext.getCurrent(),new Date(),BidRequestAuditHistory.PUBLISH_AUDIT, remark, state);
        bidRequestAuditHistoryMapper.insert(history);
        //判断审核结果(通过or拒绝),
        if(state == BidRequestAuditHistory.STATE_AUDIT){    //审核通过,修改借款对象状态,设置招标截止日期disableDate,设置审核意见remark
            bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);   //设为招标中
            bidRequest.setDisableDate(DateUtils.addDays(new Date(),bidRequest.getDisableDays()));
            bidRequest.setNote(remark);
        }else if(state == BidRequestAuditHistory.STATE_REJECT){ //审核拒绝,修改bidRequest借款对象状态,去除userinfo有标待审的状态码并更新
            bidRequest.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);   //设为被拒绝
            Userinfo applier = userinfoService.get(bidRequest.getCreateUser().getId());
            applier.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            userinfoService.update(applier);    //更新用户有标在筹的状态码
        }else{      //如果前台传过来的值通过也不是拒绝
            throw new RuntimeException("该审核结果未定义,若重试无效,请联系运维!");
        }
        //更新bidRequest
        this.update(bidRequest);
    }

    @Override
    public List<BidRequestAuditHistory> selectAuditHistoryByBidRequestId(Long bidRequestId) {
        return bidRequestAuditHistoryMapper.selectByBidRequestId(bidRequestId);
    }

    @Override
    public List<BidRequest> listIndex() {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setBidRequestStates(new int[]{BidConst.BIDREQUEST_STATE_BIDDING,
                BidConst.BIDREQUEST_STATE_PAYING_BACK,BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        qo.setCurrentPage(1);
        qo.setPageSize(BidConst.BIDREQUEST_INDEX_SIZE);
        qo.setOrderBy(BidConst.BIDREQUEST_INDEX_ORDERBY);
        qo.setOrderType(BidConst.BIDREQUEST_INDEX_ORDERTYPE);
        return bidRequestMapper.query(qo);
    }

}
