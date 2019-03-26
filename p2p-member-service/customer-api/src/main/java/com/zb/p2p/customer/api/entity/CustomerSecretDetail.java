/**
 * 
 */
package com.zb.p2p.customer.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guolitao
 *
 */
@Data
public class CustomerSecretDetail implements Serializable{

    private String mobile;
    private String idCardType;
    private String idCardNo;
    private String name;
    private String memberId;
    private String bankCardNo;
    private String bankMobile;
    private String bankName;//银行名称
	private String registerTime;

}
