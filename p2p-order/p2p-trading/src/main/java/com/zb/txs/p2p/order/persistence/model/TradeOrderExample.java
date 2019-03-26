package com.zb.txs.p2p.order.persistence.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TradeOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("TradeOrder.id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("TradeOrder.id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("TradeOrder.id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("TradeOrder.id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("TradeOrder.id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TradeOrder.id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("TradeOrder.id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("TradeOrder.id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("TradeOrder.id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("TradeOrder.id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("TradeOrder.id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("TradeOrder.id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRegisterIdIsNull() {
            addCriterion("TradeOrder.register_id is null");
            return (Criteria) this;
        }

        public Criteria andRegisterIdIsNotNull() {
            addCriterion("TradeOrder.register_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegisterIdEqualTo(Long value) {
            addCriterion("TradeOrder.register_id =", value, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdNotEqualTo(Long value) {
            addCriterion("TradeOrder.register_id <>", value, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdGreaterThan(Long value) {
            addCriterion("TradeOrder.register_id >", value, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TradeOrder.register_id >=", value, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdLessThan(Long value) {
            addCriterion("TradeOrder.register_id <", value, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdLessThanOrEqualTo(Long value) {
            addCriterion("TradeOrder.register_id <=", value, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdIn(List<Long> values) {
            addCriterion("TradeOrder.register_id in", values, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdNotIn(List<Long> values) {
            addCriterion("TradeOrder.register_id not in", values, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdBetween(Long value1, Long value2) {
            addCriterion("TradeOrder.register_id between", value1, value2, "registerId");
            return (Criteria) this;
        }

        public Criteria andRegisterIdNotBetween(Long value1, Long value2) {
            addCriterion("TradeOrder.register_id not between", value1, value2, "registerId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("TradeOrder.account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("TradeOrder.account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Long value) {
            addCriterion("TradeOrder.account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Long value) {
            addCriterion("TradeOrder.account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Long value) {
            addCriterion("TradeOrder.account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TradeOrder.account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Long value) {
            addCriterion("TradeOrder.account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Long value) {
            addCriterion("TradeOrder.account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Long> values) {
            addCriterion("TradeOrder.account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Long> values) {
            addCriterion("TradeOrder.account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Long value1, Long value2) {
            addCriterion("TradeOrder.account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Long value1, Long value2) {
            addCriterion("TradeOrder.account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("TradeOrder.member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("TradeOrder.member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(String value) {
            addCriterion("TradeOrder.member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(String value) {
            addCriterion("TradeOrder.member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(String value) {
            addCriterion("TradeOrder.member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(String value) {
            addCriterion("TradeOrder.member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLike(String value) {
            addCriterion("TradeOrder.member_id like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotLike(String value) {
            addCriterion("TradeOrder.member_id not like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<String> values) {
            addCriterion("TradeOrder.member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<String> values) {
            addCriterion("TradeOrder.member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(String value1, String value2) {
            addCriterion("TradeOrder.member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("TradeOrder.product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("TradeOrder.product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(String value) {
            addCriterion("TradeOrder.product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(String value) {
            addCriterion("TradeOrder.product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(String value) {
            addCriterion("TradeOrder.product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(String value) {
            addCriterion("TradeOrder.product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLike(String value) {
            addCriterion("TradeOrder.product_id like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotLike(String value) {
            addCriterion("TradeOrder.product_id not like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<String> values) {
            addCriterion("TradeOrder.product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<String> values) {
            addCriterion("TradeOrder.product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(String value1, String value2) {
            addCriterion("TradeOrder.product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("TradeOrder.product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("TradeOrder.product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("TradeOrder.product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("TradeOrder.product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("TradeOrder.product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("TradeOrder.product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("TradeOrder.product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("TradeOrder.product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("TradeOrder.product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("TradeOrder.product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("TradeOrder.product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.product_code not between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andSaleChannelIsNull() {
            addCriterion("TradeOrder.sale_channel is null");
            return (Criteria) this;
        }

        public Criteria andSaleChannelIsNotNull() {
            addCriterion("TradeOrder.sale_channel is not null");
            return (Criteria) this;
        }

        public Criteria andSaleChannelEqualTo(String value) {
            addCriterion("TradeOrder.sale_channel =", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelNotEqualTo(String value) {
            addCriterion("TradeOrder.sale_channel <>", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelGreaterThan(String value) {
            addCriterion("TradeOrder.sale_channel >", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.sale_channel >=", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelLessThan(String value) {
            addCriterion("TradeOrder.sale_channel <", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.sale_channel <=", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelLike(String value) {
            addCriterion("TradeOrder.sale_channel like", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelNotLike(String value) {
            addCriterion("TradeOrder.sale_channel not like", value, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelIn(List<String> values) {
            addCriterion("TradeOrder.sale_channel in", values, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelNotIn(List<String> values) {
            addCriterion("TradeOrder.sale_channel not in", values, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelBetween(String value1, String value2) {
            addCriterion("TradeOrder.sale_channel between", value1, value2, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andSaleChannelNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.sale_channel not between", value1, value2, "saleChannel");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNull() {
            addCriterion("TradeOrder.invest_amount is null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIsNotNull() {
            addCriterion("TradeOrder.invest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInvestAmountEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.invest_amount =", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.invest_amount <>", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThan(BigDecimal value) {
            addCriterion("TradeOrder.invest_amount >", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.invest_amount >=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThan(BigDecimal value) {
            addCriterion("TradeOrder.invest_amount <", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.invest_amount <=", value, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountIn(List<BigDecimal> values) {
            addCriterion("TradeOrder.invest_amount in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotIn(List<BigDecimal> values) {
            addCriterion("TradeOrder.invest_amount not in", values, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TradeOrder.invest_amount between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andInvestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TradeOrder.invest_amount not between", value1, value2, "investAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountIsNull() {
            addCriterion("TradeOrder.match_amount is null");
            return (Criteria) this;
        }

        public Criteria andMatchAmountIsNotNull() {
            addCriterion("TradeOrder.match_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMatchAmountEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.match_amount =", value, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountNotEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.match_amount <>", value, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountGreaterThan(BigDecimal value) {
            addCriterion("TradeOrder.match_amount >", value, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.match_amount >=", value, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountLessThan(BigDecimal value) {
            addCriterion("TradeOrder.match_amount <", value, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.match_amount <=", value, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountIn(List<BigDecimal> values) {
            addCriterion("TradeOrder.match_amount in", values, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountNotIn(List<BigDecimal> values) {
            addCriterion("TradeOrder.match_amount not in", values, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TradeOrder.match_amount between", value1, value2, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TradeOrder.match_amount not between", value1, value2, "matchAmount");
            return (Criteria) this;
        }

        public Criteria andMatchStatusIsNull() {
            addCriterion("TradeOrder.match_status is null");
            return (Criteria) this;
        }

        public Criteria andMatchStatusIsNotNull() {
            addCriterion("TradeOrder.match_status is not null");
            return (Criteria) this;
        }

        public Criteria andMatchStatusEqualTo(String value) {
            addCriterion("TradeOrder.match_status =", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotEqualTo(String value) {
            addCriterion("TradeOrder.match_status <>", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusGreaterThan(String value) {
            addCriterion("TradeOrder.match_status >", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.match_status >=", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusLessThan(String value) {
            addCriterion("TradeOrder.match_status <", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.match_status <=", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusLike(String value) {
            addCriterion("TradeOrder.match_status like", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotLike(String value) {
            addCriterion("TradeOrder.match_status not like", value, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusIn(List<String> values) {
            addCriterion("TradeOrder.match_status in", values, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotIn(List<String> values) {
            addCriterion("TradeOrder.match_status not in", values, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusBetween(String value1, String value2) {
            addCriterion("TradeOrder.match_status between", value1, value2, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchStatusNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.match_status not between", value1, value2, "matchStatus");
            return (Criteria) this;
        }

        public Criteria andMatchTimeIsNull() {
            addCriterion("TradeOrder.match_time is null");
            return (Criteria) this;
        }

        public Criteria andMatchTimeIsNotNull() {
            addCriterion("TradeOrder.match_time is not null");
            return (Criteria) this;
        }

        public Criteria andMatchTimeEqualTo(Date value) {
            addCriterion("TradeOrder.match_time =", value, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeNotEqualTo(Date value) {
            addCriterion("TradeOrder.match_time <>", value, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeGreaterThan(Date value) {
            addCriterion("TradeOrder.match_time >", value, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.match_time >=", value, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeLessThan(Date value) {
            addCriterion("TradeOrder.match_time <", value, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeLessThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.match_time <=", value, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeIn(List<Date> values) {
            addCriterion("TradeOrder.match_time in", values, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeNotIn(List<Date> values) {
            addCriterion("TradeOrder.match_time not in", values, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.match_time between", value1, value2, "matchTime");
            return (Criteria) this;
        }

        public Criteria andMatchTimeNotBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.match_time not between", value1, value2, "matchTime");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNull() {
            addCriterion("TradeOrder.trade_no is null");
            return (Criteria) this;
        }

        public Criteria andTradeNoIsNotNull() {
            addCriterion("TradeOrder.trade_no is not null");
            return (Criteria) this;
        }

        public Criteria andTradeNoEqualTo(String value) {
            addCriterion("TradeOrder.trade_no =", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotEqualTo(String value) {
            addCriterion("TradeOrder.trade_no <>", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThan(String value) {
            addCriterion("TradeOrder.trade_no >", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.trade_no >=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThan(String value) {
            addCriterion("TradeOrder.trade_no <", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.trade_no <=", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoLike(String value) {
            addCriterion("TradeOrder.trade_no like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotLike(String value) {
            addCriterion("TradeOrder.trade_no not like", value, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoIn(List<String> values) {
            addCriterion("TradeOrder.trade_no in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotIn(List<String> values) {
            addCriterion("TradeOrder.trade_no not in", values, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoBetween(String value1, String value2) {
            addCriterion("TradeOrder.trade_no between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andTradeNoNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.trade_no not between", value1, value2, "tradeNo");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("TradeOrder.pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("TradeOrder.pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(String value) {
            addCriterion("TradeOrder.pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(String value) {
            addCriterion("TradeOrder.pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(String value) {
            addCriterion("TradeOrder.pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(String value) {
            addCriterion("TradeOrder.pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLike(String value) {
            addCriterion("TradeOrder.pay_status like", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotLike(String value) {
            addCriterion("TradeOrder.pay_status not like", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<String> values) {
            addCriterion("TradeOrder.pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<String> values) {
            addCriterion("TradeOrder.pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayCodeIsNull() {
            addCriterion("TradeOrder.pay_code is null");
            return (Criteria) this;
        }

        public Criteria andPayCodeIsNotNull() {
            addCriterion("TradeOrder.pay_code is not null");
            return (Criteria) this;
        }

        public Criteria andPayCodeEqualTo(String value) {
            addCriterion("TradeOrder.pay_code =", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotEqualTo(String value) {
            addCriterion("TradeOrder.pay_code <>", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeGreaterThan(String value) {
            addCriterion("TradeOrder.pay_code >", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_code >=", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeLessThan(String value) {
            addCriterion("TradeOrder.pay_code <", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_code <=", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeLike(String value) {
            addCriterion("TradeOrder.pay_code like", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotLike(String value) {
            addCriterion("TradeOrder.pay_code not like", value, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeIn(List<String> values) {
            addCriterion("TradeOrder.pay_code in", values, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotIn(List<String> values) {
            addCriterion("TradeOrder.pay_code not in", values, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_code between", value1, value2, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayCodeNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_code not between", value1, value2, "payCode");
            return (Criteria) this;
        }

        public Criteria andPayMsgIsNull() {
            addCriterion("TradeOrder.pay_msg is null");
            return (Criteria) this;
        }

        public Criteria andPayMsgIsNotNull() {
            addCriterion("TradeOrder.pay_msg is not null");
            return (Criteria) this;
        }

        public Criteria andPayMsgEqualTo(String value) {
            addCriterion("TradeOrder.pay_msg =", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgNotEqualTo(String value) {
            addCriterion("TradeOrder.pay_msg <>", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgGreaterThan(String value) {
            addCriterion("TradeOrder.pay_msg >", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_msg >=", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgLessThan(String value) {
            addCriterion("TradeOrder.pay_msg <", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_msg <=", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgLike(String value) {
            addCriterion("TradeOrder.pay_msg like", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgNotLike(String value) {
            addCriterion("TradeOrder.pay_msg not like", value, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgIn(List<String> values) {
            addCriterion("TradeOrder.pay_msg in", values, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgNotIn(List<String> values) {
            addCriterion("TradeOrder.pay_msg not in", values, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_msg between", value1, value2, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayMsgNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_msg not between", value1, value2, "payMsg");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("TradeOrder.pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("TradeOrder.pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("TradeOrder.pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("TradeOrder.pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("TradeOrder.pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("TradeOrder.pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("TradeOrder.pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("TradeOrder.pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayNoIsNull() {
            addCriterion("TradeOrder.pay_no is null");
            return (Criteria) this;
        }

        public Criteria andPayNoIsNotNull() {
            addCriterion("TradeOrder.pay_no is not null");
            return (Criteria) this;
        }

        public Criteria andPayNoEqualTo(String value) {
            addCriterion("TradeOrder.pay_no =", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotEqualTo(String value) {
            addCriterion("TradeOrder.pay_no <>", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoGreaterThan(String value) {
            addCriterion("TradeOrder.pay_no >", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_no >=", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLessThan(String value) {
            addCriterion("TradeOrder.pay_no <", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.pay_no <=", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoLike(String value) {
            addCriterion("TradeOrder.pay_no like", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotLike(String value) {
            addCriterion("TradeOrder.pay_no not like", value, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoIn(List<String> values) {
            addCriterion("TradeOrder.pay_no in", values, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotIn(List<String> values) {
            addCriterion("TradeOrder.pay_no not in", values, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_no between", value1, value2, "payNo");
            return (Criteria) this;
        }

        public Criteria andPayNoNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.pay_no not between", value1, value2, "payNo");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIsNull() {
            addCriterion("TradeOrder.refund_status is null");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIsNotNull() {
            addCriterion("TradeOrder.refund_status is not null");
            return (Criteria) this;
        }

        public Criteria andRefundStatusEqualTo(String value) {
            addCriterion("TradeOrder.refund_status =", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotEqualTo(String value) {
            addCriterion("TradeOrder.refund_status <>", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusGreaterThan(String value) {
            addCriterion("TradeOrder.refund_status >", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.refund_status >=", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLessThan(String value) {
            addCriterion("TradeOrder.refund_status <", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.refund_status <=", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusLike(String value) {
            addCriterion("TradeOrder.refund_status like", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotLike(String value) {
            addCriterion("TradeOrder.refund_status not like", value, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusIn(List<String> values) {
            addCriterion("TradeOrder.refund_status in", values, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotIn(List<String> values) {
            addCriterion("TradeOrder.refund_status not in", values, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusBetween(String value1, String value2) {
            addCriterion("TradeOrder.refund_status between", value1, value2, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundStatusNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.refund_status not between", value1, value2, "refundStatus");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIsNull() {
            addCriterion("TradeOrder.refund_time is null");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIsNotNull() {
            addCriterion("TradeOrder.refund_time is not null");
            return (Criteria) this;
        }

        public Criteria andRefundTimeEqualTo(Date value) {
            addCriterion("TradeOrder.refund_time =", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotEqualTo(Date value) {
            addCriterion("TradeOrder.refund_time <>", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeGreaterThan(Date value) {
            addCriterion("TradeOrder.refund_time >", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.refund_time >=", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeLessThan(Date value) {
            addCriterion("TradeOrder.refund_time <", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeLessThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.refund_time <=", value, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeIn(List<Date> values) {
            addCriterion("TradeOrder.refund_time in", values, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotIn(List<Date> values) {
            addCriterion("TradeOrder.refund_time not in", values, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.refund_time between", value1, value2, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundTimeNotBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.refund_time not between", value1, value2, "refundTime");
            return (Criteria) this;
        }

        public Criteria andRefundNoIsNull() {
            addCriterion("TradeOrder.refund_no is null");
            return (Criteria) this;
        }

        public Criteria andRefundNoIsNotNull() {
            addCriterion("TradeOrder.refund_no is not null");
            return (Criteria) this;
        }

        public Criteria andRefundNoEqualTo(String value) {
            addCriterion("TradeOrder.refund_no =", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoNotEqualTo(String value) {
            addCriterion("TradeOrder.refund_no <>", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoGreaterThan(String value) {
            addCriterion("TradeOrder.refund_no >", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.refund_no >=", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoLessThan(String value) {
            addCriterion("TradeOrder.refund_no <", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.refund_no <=", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoLike(String value) {
            addCriterion("TradeOrder.refund_no like", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoNotLike(String value) {
            addCriterion("TradeOrder.refund_no not like", value, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoIn(List<String> values) {
            addCriterion("TradeOrder.refund_no in", values, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoNotIn(List<String> values) {
            addCriterion("TradeOrder.refund_no not in", values, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoBetween(String value1, String value2) {
            addCriterion("TradeOrder.refund_no between", value1, value2, "refundNo");
            return (Criteria) this;
        }

        public Criteria andRefundNoNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.refund_no not between", value1, value2, "refundNo");
            return (Criteria) this;
        }

        public Criteria andCashStatusIsNull() {
            addCriterion("TradeOrder.cash_status is null");
            return (Criteria) this;
        }

        public Criteria andCashStatusIsNotNull() {
            addCriterion("TradeOrder.cash_status is not null");
            return (Criteria) this;
        }

        public Criteria andCashStatusEqualTo(String value) {
            addCriterion("TradeOrder.cash_status =", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotEqualTo(String value) {
            addCriterion("TradeOrder.cash_status <>", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusGreaterThan(String value) {
            addCriterion("TradeOrder.cash_status >", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.cash_status >=", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusLessThan(String value) {
            addCriterion("TradeOrder.cash_status <", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.cash_status <=", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusLike(String value) {
            addCriterion("TradeOrder.cash_status like", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotLike(String value) {
            addCriterion("TradeOrder.cash_status not like", value, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusIn(List<String> values) {
            addCriterion("TradeOrder.cash_status in", values, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotIn(List<String> values) {
            addCriterion("TradeOrder.cash_status not in", values, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusBetween(String value1, String value2) {
            addCriterion("TradeOrder.cash_status between", value1, value2, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashStatusNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.cash_status not between", value1, value2, "cashStatus");
            return (Criteria) this;
        }

        public Criteria andCashTimeIsNull() {
            addCriterion("TradeOrder.cash_time is null");
            return (Criteria) this;
        }

        public Criteria andCashTimeIsNotNull() {
            addCriterion("TradeOrder.cash_time is not null");
            return (Criteria) this;
        }

        public Criteria andCashTimeEqualTo(Date value) {
            addCriterion("TradeOrder.cash_time =", value, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeNotEqualTo(Date value) {
            addCriterion("TradeOrder.cash_time <>", value, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeGreaterThan(Date value) {
            addCriterion("TradeOrder.cash_time >", value, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.cash_time >=", value, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeLessThan(Date value) {
            addCriterion("TradeOrder.cash_time <", value, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeLessThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.cash_time <=", value, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeIn(List<Date> values) {
            addCriterion("TradeOrder.cash_time in", values, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeNotIn(List<Date> values) {
            addCriterion("TradeOrder.cash_time not in", values, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.cash_time between", value1, value2, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashTimeNotBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.cash_time not between", value1, value2, "cashTime");
            return (Criteria) this;
        }

        public Criteria andCashAmountIsNull() {
            addCriterion("TradeOrder.cash_amount is null");
            return (Criteria) this;
        }

        public Criteria andCashAmountIsNotNull() {
            addCriterion("TradeOrder.cash_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCashAmountEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.cash_amount =", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountNotEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.cash_amount <>", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountGreaterThan(BigDecimal value) {
            addCriterion("TradeOrder.cash_amount >", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.cash_amount >=", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountLessThan(BigDecimal value) {
            addCriterion("TradeOrder.cash_amount <", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TradeOrder.cash_amount <=", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountIn(List<BigDecimal> values) {
            addCriterion("TradeOrder.cash_amount in", values, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountNotIn(List<BigDecimal> values) {
            addCriterion("TradeOrder.cash_amount not in", values, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TradeOrder.cash_amount between", value1, value2, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TradeOrder.cash_amount not between", value1, value2, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andHisFlagIsNull() {
            addCriterion("TradeOrder.his_flag is null");
            return (Criteria) this;
        }

        public Criteria andHisFlagIsNotNull() {
            addCriterion("TradeOrder.his_flag is not null");
            return (Criteria) this;
        }

        public Criteria andHisFlagEqualTo(String value) {
            addCriterion("TradeOrder.his_flag =", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotEqualTo(String value) {
            addCriterion("TradeOrder.his_flag <>", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagGreaterThan(String value) {
            addCriterion("TradeOrder.his_flag >", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.his_flag >=", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagLessThan(String value) {
            addCriterion("TradeOrder.his_flag <", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.his_flag <=", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagLike(String value) {
            addCriterion("TradeOrder.his_flag like", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotLike(String value) {
            addCriterion("TradeOrder.his_flag not like", value, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagIn(List<String> values) {
            addCriterion("TradeOrder.his_flag in", values, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotIn(List<String> values) {
            addCriterion("TradeOrder.his_flag not in", values, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagBetween(String value1, String value2) {
            addCriterion("TradeOrder.his_flag between", value1, value2, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andHisFlagNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.his_flag not between", value1, value2, "hisFlag");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("TradeOrder.version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("TradeOrder.version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("TradeOrder.version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("TradeOrder.version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("TradeOrder.version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("TradeOrder.version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("TradeOrder.version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("TradeOrder.version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("TradeOrder.version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("TradeOrder.version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("TradeOrder.version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("TradeOrder.version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("TradeOrder.create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("TradeOrder.create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("TradeOrder.create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("TradeOrder.create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("TradeOrder.create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("TradeOrder.create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("TradeOrder.create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("TradeOrder.create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("TradeOrder.modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("TradeOrder.modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("TradeOrder.modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("TradeOrder.modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("TradeOrder.modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("TradeOrder.modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("TradeOrder.modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("TradeOrder.modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("TradeOrder.modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("TradeOrder.modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("TradeOrder.create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("TradeOrder.create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("TradeOrder.create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("TradeOrder.create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("TradeOrder.create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("TradeOrder.create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("TradeOrder.create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("TradeOrder.create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("TradeOrder.create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("TradeOrder.create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("TradeOrder.create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andModifyByIsNull() {
            addCriterion("TradeOrder.modify_by is null");
            return (Criteria) this;
        }

        public Criteria andModifyByIsNotNull() {
            addCriterion("TradeOrder.modify_by is not null");
            return (Criteria) this;
        }

        public Criteria andModifyByEqualTo(String value) {
            addCriterion("TradeOrder.modify_by =", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotEqualTo(String value) {
            addCriterion("TradeOrder.modify_by <>", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByGreaterThan(String value) {
            addCriterion("TradeOrder.modify_by >", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByGreaterThanOrEqualTo(String value) {
            addCriterion("TradeOrder.modify_by >=", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLessThan(String value) {
            addCriterion("TradeOrder.modify_by <", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLessThanOrEqualTo(String value) {
            addCriterion("TradeOrder.modify_by <=", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLike(String value) {
            addCriterion("TradeOrder.modify_by like", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotLike(String value) {
            addCriterion("TradeOrder.modify_by not like", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByIn(List<String> values) {
            addCriterion("TradeOrder.modify_by in", values, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotIn(List<String> values) {
            addCriterion("TradeOrder.modify_by not in", values, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByBetween(String value1, String value2) {
            addCriterion("TradeOrder.modify_by between", value1, value2, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotBetween(String value1, String value2) {
            addCriterion("TradeOrder.modify_by not between", value1, value2, "modifyBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}