package com.zb.p2p.trade.service.message.api;

import lombok.Data;

/**
 * 还款通知短信内容
 *
 * 尊敬的客户，您好。您${loanYear}年${loanMonth}月${loanDay}日通过马上贷平台申请的借款，到期应还本息${totalAmount}元，请于${month}月${day}日前还款至约定银行账户。
 * @author zhangxin
 *
 */
@Data
public class RepaymentNoticeMessageInfo {
    
    String loanYear;
    String loanMonth;
    String loanDay;
    
    String totalAmount;
    
    String month;
    String day;
             
}
