package com.zb.p2p.trade.common.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> 兑付金额套件 </p>
 *
 * @author Vinson
 * @version CashAmountSuite.java v1.0 2018/4/21 16:25 Zhengwenquan Exp $
 */
@Data
public class CashAmountSuite {

    // 本金
    private BigDecimal principle;

    // 利息
    private BigDecimal interest;

    // 手续费
    private BigDecimal fee;

    public CashAmountSuite() {
        this.principle = BigDecimal.ZERO;
        this.interest = BigDecimal.ZERO;
        this.fee = BigDecimal.ZERO;
    }

    public CashAmountSuite(BigDecimal principle, BigDecimal interest) {
        this.principle = principle;
        this.interest = interest;
        this.fee = BigDecimal.ZERO;
    }

    public CashAmountSuite(BigDecimal principle, BigDecimal interest, BigDecimal fee) {
        this.principle = principle;
        this.interest = interest;
        this.fee = fee;
    }

    public static CashAmountSuite of(){ return new CashAmountSuite();}

    public void set(BigDecimal principle, BigDecimal interest){
        this.principle = principle;
        this.interest = interest;
    }

    public CashAmountSuite add(CashAmountSuite other){
        this.principle = this.principle.add(other.getPrinciple());
        this.interest = this.interest.add(other.getInterest());

        return new CashAmountSuite(this.principle, this.interest);
    }

    public CashAmountSuite subtract(CashAmountSuite other){
        this.principle = this.principle.add(other.getPrinciple());
        this.interest = this.interest.add(other.getInterest());

        return new CashAmountSuite(this.principle, this.interest);
    }

    /**
     * 累计总额
     * @return
     */
    public BigDecimal getTotal() {
        return this.principle.add(this.interest).add(this.fee);
    }

}
