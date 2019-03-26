package com.zb.fincore.pms.facade.product;

import com.zb.fincore.pms.common.dto.BaseResponse;

/**
 * 功能: 产品数据库服务接口
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/4/6 0006 16:55
 * 版本: V1.0
 */
public interface ProductJobServiceFacade {

    /**
     * 产品募集期开始 JOB调用
     * @return
     */
    BaseResponse startProductRaising();

//    /**
//     * 产品待成立 JOB调用
//     * 募集期结束 --》产品待成立    JOB调用
//     * @return
//     * @throws Exception
//     */
//    BaseResponse putProductWaitingEstablish();
    
    /**
     * 产品存续期 JOB调用
     * 募集期结束 --》募集完成    JOB调用
     * @return
     * @throws Exception
     */
    BaseResponse putProductValuing();

//    /**
//     * 产品计息开始 --》产品存续期
//     * description : 将产品状态为 已成立的产品 设置为存续期状态
//     * @return
//     */
//    BaseResponse putProductInValueOfJob();
//    
//    /**
//     * 产品存续期结束 --》产品到期 JOB调用
//     * @return
//     */
//    BaseResponse putProductOutValue();

//    /**
//     * JOB 阶梯信息更新
//     * description : 阶梯产品每个收益阶段结束，更新为新的阶段阶梯信息
//     * @return
//     */
//    BaseResponse updateProductLadderInfo();

}
