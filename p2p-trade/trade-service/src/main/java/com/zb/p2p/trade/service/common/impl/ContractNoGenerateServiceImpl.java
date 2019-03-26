package com.zb.p2p.trade.service.common.impl;

import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.trade.service.common.ContractNoGenerateService;
import org.springframework.stereotype.Service;

/**
 * Created by mengkai on 2017/9/5.
 */
@Service
public class ContractNoGenerateServiceImpl implements ContractNoGenerateService {
    public static final String DATE_FORMAT = "yyMMdd";

    @Override
    public String generateContractNo(String codePrefix, String channel,String id) {
        String businessCode = "";
        String currentDate = DateUtils.format(DateUtils.now(), DATE_FORMAT);
        StringBuffer sb = new StringBuffer();
        sb.append(codePrefix).append(channel).append(currentDate).append(id);
        businessCode = sb.toString();
        return businessCode;
    }
}
