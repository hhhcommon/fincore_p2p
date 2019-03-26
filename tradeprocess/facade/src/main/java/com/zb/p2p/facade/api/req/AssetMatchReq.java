package com.zb.p2p.facade.api.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资产匹配请求
 *
 * @author
 * @create 2018-01-08 19:57
 */
@Data
public class AssetMatchReq implements Serializable{

    private List<String> productCodes;
}
