package com.zb.p2p.customer.common.constant;

import com.zb.p2p.customer.common.enums.AccountPurposeEnum;

import java.util.Arrays;
import java.util.List;

public interface AppConstant {

//	String SOURCE_ID_QJS_YST = "QJS_YST";
	
	String SOURCE_ID_MSD = "MSD";

	String CHANNEL_CODE_SYNC_TXS = "TXS";
	
	String RESP_CODE_SUCCESS = "0000";
	/** 账户不存在 */
	String RESP_CODE_NO_ACCOUNT = "B0000";

	String MSD_RESULT_SUCCESS_CODE = "000000";
	
	String P2P_REDIS_KEY_TXS_MEMBERINFO = "p2p_redis_key_txs_memberInfo:";
	
	String P2P_REDIS_KEY_CHECKCODE = "p2p_redis_key_checkcode:";
	
	String P2P_REDIS_KEY_AUTHREGISTER = "p2p_redis_key_authregister:";

	// 实名认证结果一致状态码
	String REALNAME_VALIDATE_CODE_SUCCESS = "01";

	// 个人支持的出借账户
	List<String> PERSONAL_ACCOUNT_PURPOSE = Arrays.asList(new String[]{AccountPurposeEnum._101.getCode()
			, AccountPurposeEnum._102.getCode()});

}
