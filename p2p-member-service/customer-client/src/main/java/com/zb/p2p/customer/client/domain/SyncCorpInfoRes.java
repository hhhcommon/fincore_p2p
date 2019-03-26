package com.zb.p2p.customer.client.domain;

import lombok.Data;

import java.util.Date;

/**
 * <p> 企业/机构会员信息同步结果 </p>
 *
 * @author Vinson
 * @version SyncCorpInfoRes.java v1.0 2018/3/26 17:35 Zhengwenquan Exp $
 */
@Data
public class SyncCorpInfoRes {

    private Long id;

    private Long memberId; // 企业/机构会员id
    private String sourceId; //来源标示: TXSTZ-唐小僧投资;MSDTZ-马上贷投资;MSDJK-马上贷借款
    private String memberType; // 会员类型：CORP-机构	String	是
    private String corpIdType;    //机构证件类型：BUSINESSLICENSE-营业执照；CREDITCODE-社会统一信用代码；ORGANIZATIONCODE-组织机构代码；	String	是
    private String corpIdNo;    //机构证件号码	String	是
    private String corpName;    //机构名称	String	是
    private String identityType;    //法人证件类型：IDCARD-身份证	String	是
    private String identityNo;    //法人证件号码	String	是
    private String identityName;    //法人姓名	String	是
    private String mobile;    //法人手机号码	String	是
    private String accountType;    //账户类型：SINACQGH-新浪存钱罐户; MSDTZH-马上贷投资户; MSDJKH-马上贷借款户	String	是
    private String payUserId    ;    //账务系统生成的账户id	String	是
    private String status;    //会员状态: NORMAL-正常;FORBIDDEN-禁止登录;	String	是
    private Date created;    //创建时间	Date	是
    private Long createdBy;//	创建人id	Long	是
    private Date modified;    //修改时间	Date	是
    private Long modifiedBy;

}
