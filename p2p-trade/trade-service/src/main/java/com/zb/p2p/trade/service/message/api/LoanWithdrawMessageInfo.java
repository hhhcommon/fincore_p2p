package com.zb.p2p.trade.service.message.api;

import lombok.Data;

/**
 * 放款短信内容
 * 
 * 尊敬的${userName}先生/女士，您${loanYear}年${loanMonth}月${loanDay}日通过马上贷平台申请的借款${totalAmount}元
        	 * 已于${month}月${day}日${hour}点${mins}分发放至尾号${bankNo}的银行卡，请您查收。 
 * @author tangqingqing
 *
 *
 *尊敬的客户，您好。您${loanYear}年${loanMonth}月${loanDay}日通过马上贷平台申请的借款${totalAmount}元
 *已于${month}月${day}日${hour}点${mins}分发放至尾号${bankNo}的银行账号，请您查收。
 *
 *
 */
@Data
public class LoanWithdrawMessageInfo {
     
   // String userName;
    
    String loanYear;
    String loanMonth;
    String loanDay;
    
    String totalAmount;
    
    String month;
    String day;
    String hour;
    String mins;
    
    String bankNo;
             
}
