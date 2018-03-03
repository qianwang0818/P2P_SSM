package com.xmg.p2p.business.service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.Bid;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.mapper.BidMapper;
import com.xmg.p2p.business.mapper.BidRequestAuditHistoryMapper;
import com.xmg.p2p.business.mapper.BidRequestMapper;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.service.IAccountFlowService;
import com.xmg.p2p.business.service.IBidRequestService;
import com.xmg.p2p.business.service.ISystemAccountService;
import com.xmg.p2p.business.util.CalculatetUtil;
import com.xmg.p2p.business.util.DecimalFormatUtil;
import com.xmg.p2p.exception.BidException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private BidMapper bidMapper;

    @Autowired
    private IAccountFlowService accountFlowService;

    @Autowired
    private ISystemAccountService systemAccountService;


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

    /**借款申请*/
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

    /**根据一个标的查询其对应的审核历史*/
    @Override
    public List<BidRequestAuditHistory> selectAuditHistoryByBidRequestId(Long bidRequestId) {
        return bidRequestAuditHistoryMapper.selectByBidRequestId(bidRequestId);
    }

    /**展示首页的借款标的*/
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

    /**投资者申请投标*/
    @Override
    public void bid(Long bidRequestId, BigDecimal amount) throws RuntimeException {
        BidRequest br = this.get(bidRequestId);
        Account account = accountService.getCurrent();
        Logininfo logininfo = UserContext.getCurrent();
        BigDecimal remainAmount = br.getRemainAmount();
        BigDecimal minBidAmount = br.getMinBidAmount();
        //检查判断
        //1.借款标的存在
        if(br==null){
            throw new BidException("该借款标的不存在!");
        }
        //2.借款状态为招标中
        if(br.getBidRequestState()!=BidConst.BIDREQUEST_STATE_BIDDING){
            throw new BidException("该借款标的不在招标中,请刷新页面!");
        }
        //3.当前投标用户不是借款标的申请人
        if(br.getCreateUser().equals(logininfo.getId())){
            throw new BidException("您不能投资自己发布的借款标的!");
        }
        //4.申请投标金额≤当前用户账户余额
        if(amount.compareTo(account.getUsableAmount())>0){
            throw new BidException("您的账户可用余额小于本次申请投标金额!");
        }
        //5.申请投标金额≥借款的最小投标金额
        if(amount.compareTo(minBidAmount)<0){
            throw new BidException("本次投标金额小于该借款标的限制的最小投标金额!");
        }
        //6.申请投标金额≤借款标的剩余可投金额
        if(amount.compareTo(remainAmount)>0){
            throw new BidException("本次投标金额大于该借款标的剩余可投金额!");
        }
        //7.如果零＜投标后剩余标的＜最小投标金额,要求把余标全部投完 (现在剩余标的额-本次投标额)<最小投标额
        if( (remainAmount.subtract(amount).compareTo(BigDecimal.ZERO) > 0) && (remainAmount.subtract(amount).compareTo(minBidAmount) < 0) ){
            throw new BidException("投标后的余标金额请不要小于最小投标金额("+ DecimalFormatUtil.formatBigDecimal(minBidAmount,2) +")!");
        }
        //8.其它的判断,例如:
        /*投标金额不允许有小数点,方便计算利息.
        投标总人数不能超过两百人,否则认定为非法集资.
        同一个人对同一标的可以多次投资,次数不能超过五次.总金额不能超过20%.*/

        //执行投标
        //1.创建一个投标对象,设置相关属性
        Bid bid = new Bid(br.getCurrentRate(),amount,br.getId(),br.getTitle(), logininfo,new Date());
        bidMapper.insert(bid);
        //2.得到投标人账户,修改账户信息
        account.subtractUsableAmount(amount);
        account.addFreezedAmount(amount);
        //3.生成一条投标流水
        accountFlowService.bidFlow(bid,account);
        //4.修改借款标的相关信息
        br.addBidCount();
        br.addCurrentSum(amount);

        //判断当前标是否投满
        if(br.getRemainAmount().compareTo(BigDecimal.ZERO)==0){       //如果投满,修改标的状态为满标一审.
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1);
        }

        //更新对象
        this.accountService.update(account);
        this.update(br);
    }

    /**满标一审审核*/
    @Override
    public void fullAudit1(BidRequestAuditHistory form) {
        Long bidRequestId = form.getId();
        int state = form.getState();
        //得到借款对象,判断对象
        BidRequest br = this.get(bidRequestId);
        if(br==null){
            throw new BidException("该借款标的不存在!");
        }
        if(br.getBidRequestState()!=BidConst.BIDREQUEST_STATE_APPROVE_PENDING_1){
            throw new BidException("该借款标的不在满标一审中,请刷新页面!");
        }

        //创建一个借款审核记录对象
        BidRequestAuditHistory history = new BidRequestAuditHistory(bidRequestId, br.getCreateUser(),new Date(),
                UserContext.getCurrent(), new Date(), BidRequestAuditHistory.FULL_AUDIT_1, form.getRemark(), state);
        bidRequestAuditHistoryMapper.insert(history);

        //判断审核结果
        if(state==BidRequestAuditHistory.FULL_AUDIT_1){    //如果审核通过 把借款状态设置为满标二审
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2);
        }else if(state==BidRequestAuditHistory.STATE_REJECT){   //如果审核不通过: 1.把借款状态设置为满审拒绝 2.退款 3.将借款人有标在借状态移除
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
            this.returnBidMoney(br);
            Userinfo applierUserinfo = userinfoService.get(br.getCreateUser().getId());
            applierUserinfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            userinfoService.update(applierUserinfo);
        }

        //更新借款对象
        this.update(br);
    }

    /**满标二审审核*/
    @Override
    public void fullAudit2(BidRequestAuditHistory form) {
        Long bidRequestId = form.getId();
        int state = form.getState();
        //得到借款对象,判断对象
        BidRequest br = this.get(bidRequestId);
        if(br==null){
            throw new BidException("该借款标的不存在!");
        }
        if(br.getBidRequestState()!=BidConst.BIDREQUEST_STATE_APPROVE_PENDING_2){
            throw new BidException("该借款标的不在满标二审中,请刷新页面!");
        }

        //创建一个借款审核记录对象
        BidRequestAuditHistory history = new BidRequestAuditHistory(bidRequestId, br.getCreateUser(),new Date(),
                UserContext.getCurrent(), new Date(), BidRequestAuditHistory.FULL_AUDIT_2, form.getRemark(), state);
        bidRequestAuditHistoryMapper.insert(history);

        //判断审核结果
        Long applierId = br.getCreateUser().getId();
        Userinfo applierUserinfo = userinfoService.get(applierId);
        if(state==BidRequestAuditHistory.STATE_AUDIT){    //如果审核通过
            BigDecimal amout = br.getBidRequestAmount();

            //1.对借款标的  修改借款状态为还款中
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_PAYING_BACK);

            //2.对借款人  账户余额增加 生成收款流水 增加待还本息 减少可用信用 移除借款人有标在借状态 支付借款手续费
            Account applierAccount = accountService.get(applierId);
            applierAccount.addUsableAmount(amout);
            accountFlowService.borrowSuccessFlow(br, applierAccount);
            applierAccount.addUnReturnAmount(amout.add(br.getTotalRewardAmount()));     //设置待还本息
            applierAccount.subtractRemainBorrowLimit(amout);
            applierUserinfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            BigDecimal managementChargeFee = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
            applierAccount.subtractUsableAmount(managementChargeFee);      //支付借款手续费
            accountFlowService.borrowChargeFeeFlow(managementChargeFee,br,applierAccount);

            //3.对平台  平台收取借款手续费 平台账户流水
            systemAccountService.chargeBorrowFee(br, managementChargeFee);

            //4.对投资人  遍历投标 减少冻结金 生成成功投标流水 计算待收利息待收本金
            // 汇总利息,用于最后一个投标的用户的利息计算
            BigDecimal totalBidInterest = BidConst.ZERO;        //定义初始化的利息为零
            //遍历该标的下每一笔投资
            Map<Long, Account> updateMap = new HashMap<Long, Account>();
            List<Bid> bids = br.getBids();
            for (int i=1 ; i<=bids.size() ; i++) {
                Bid bid = bids.get(i-1);
                // 4.1 减少投资人的冻结金额;
                Long bidUserId = bid.getBidUser().getId();
                Account bidAccount = updateMap.get(bidUserId);
                if (bidAccount == null){    //如果Map缓存中没有才去数据库找
                    bidAccount = this.accountService.get(bidUserId);
                    updateMap.put(bidUserId, bidAccount);
                }
                bidAccount.subtractFreezedAmount(bid.getAvailableAmount());     //减少冻结金额
                // 4.2 生成成功投标流水
                this.accountFlowService.bidSuccessFlow(bid, bidAccount);
                // 4.3 计算待收本金和待收利息
                bidAccount.addUnReceivePrincipal(bid.getAvailableAmount());     //增加待收本金: 待收本金=本次的投标金额
                BigDecimal bidInterest = BidConst.ZERO;     //定义一个待收利息容器,用于一会接收计算得到的待收利息额
                if (i < bids.size()) {  //如果当前投标不是最后一个投标,累加利息
                    // 待收利息=(投标金额/借款总金额)*借款总回报利息
                    bidInterest = ( bid.getAvailableAmount()
                            .divide(br.getBidRequestAmount(),BidConst.CAL_SCALE,RoundingMode.HALF_UP) )
                            .multiply(br.getTotalRewardAmount());
                    bidInterest = DecimalFormatUtil.formatBigDecimal(bidInterest, BidConst.STORE_SCALE);    //计算精度8位,存储精度4位
                    // 累计利息增加,增加每次投标的利息
                    totalBidInterest = totalBidInterest.add(bidInterest);
                } else {            // 如果当前投标是整个投标列表中的最后一个投标. 此投标的利息=借款总回报利息-前面累加的投标利息
                    bidInterest = br.getTotalRewardAmount().subtract(totalBidInterest);
                }
                bidAccount.addUnReceiveInterest(bidInterest);   //增加待收利息
            }

            //5.满标二审之后的流程(还款)对满标二审的影响  生成还款对象和回款对象
            // **4生成还款对象和回款对象
            //createPaymentSchedules(br);

            for (Account bidAccount : updateMap.values()){      //更新投资人account
                this.accountService.update(bidAccount);
            }
            this.accountService.update(applierAccount);     //更新借款人account

        }else if(state==BidRequestAuditHistory.STATE_REJECT){   //如果审核不通过: 1.把借款状态设置为满审拒绝 2.退款 3.将借款人有标在借状态移除
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_REJECTED);
            this.returnBidMoney(br);
            applierUserinfo.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
        }

        //更新对象
        userinfoService.update(applierUserinfo);
        this.update(br);
    }

    /**退回所有投资资金*/
    private void returnBidMoney(BidRequest br) {
        //定义一个Map用来缓存Account
        Map<Long, Account> updateMap = new HashMap<>();

        //遍历投标列表,针对每一个投标进行退款
        for (Bid bid : br.getBids()) {
            Long accountId = bid.getBidUser().getId();
            //找到投标人对应的account
            Account account = updateMap.get(accountId);
            if(account == null){    //如果是第一次遍历到这个用户,就去数据库查找,并放入缓存Map中
                account = accountService.get(accountId);
                updateMap.put(accountId,account);
            }
            BigDecimal amount = bid.getAvailableAmount();
            //账户可用余额增加,冻结金额减少
            account.addUsableAmount(amount);
            account.subtractFreezedAmount(amount);
            //生成账户流水
            accountFlowService.returnBidMoney(bid,account);

        }
        //更新账户
        for (Account account : updateMap.values()) {
            accountService.update(account);
        }
    }

}
