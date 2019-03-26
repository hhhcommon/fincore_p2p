package com.zb.p2p.service.match.schedule;
/*
package com.zb.p2p.service.match.impl;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.zb.fincore.common.utils.CollectionUtils;
import com.zb.fincore.common.utils.DateUtils;
import com.zb.p2p.dao.master.InterfaceRetryDAO;
import com.zb.p2p.entity.InterfaceRetryEntity;
import com.zb.p2p.enums.InterfaceRetryBusinessTypeEnum;
import com.zb.p2p.enums.ReservationStatusEnum;
import com.zb.p2p.facade.api.resp.product.ProductDTO;
import com.zb.p2p.facade.service.internal.OrderInternalService;
import com.zb.p2p.facade.service.internal.ProductInternalService;
import com.zb.p2p.facade.service.internal.dto.ReservationOrderDTO;
import com.zb.p2p.service.callback.TXSCallBackService;
import com.zb.p2p.service.callback.api.req.NotifyUnMatchedOrderReq;
import com.zb.p2p.service.callback.api.resp.NotifyResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

*/
/**
 * 日切未匹配资金通知唐小僧
 * @author zhangxin
 * @create 2017-09-07 17:23
 *//*

public class UnMatchOrderNotifyTask implements IScheduleTaskDealSingle<ReservationOrderDTO>{
    private static Logger logger = LoggerFactory.getLogger(UnMatchOrderNotifyTask.class);

    @Autowired
    private OrderInternalService orderInternalService;

    @Autowired
    private ProductInternalService productInternalService;

    @Autowired
    private TXSCallBackService txsCallBackService;

    @Autowired
    private InterfaceRetryDAO interfaceRetryDAO;

    @Override
    public boolean execute(ReservationOrderDTO reservationOrderDTO, String s) throws Exception {
        return true;
    }

    @Override
    public List<ReservationOrderDTO> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
        logger.info("UnMatchReservationOrderNotifyTask start running ....");

        List<String> dayCutProductCodes = new ArrayList<>();
        List<String> productCodeList = new ArrayList<String>();

        //调用pms接口查询所有日切产品列表
        List<ProductDTO> productList = productInternalService.queryProductListByDate(null);
        logger.info("UnMatchReservationOrderNotifyTask call pms query product list size :{}", null == productList ? 0 : productList.size());
        if(!CollectionUtils.isNullOrEmpty(productList)){
            //日切的所有产品列表
            Collection<String> dayCutProductCodeList = CollectionUtils.collect(productList, "productCode");
            dayCutProductCodes = new ArrayList<>(dayCutProductCodeList);


            //根据日切的所有产品列表 得出需要退款的产品列表
            Map<String, Object> params = new HashMap<String, Object>();
            List<String> reservationStatusList = new ArrayList<String>();
            reservationStatusList.add(ReservationStatusEnum.PARTLY_RESERVATION_SUCCESS.getCode());
            reservationStatusList.add(ReservationStatusEnum.RESERVATION_UNMATCHED.getCode());
            reservationStatusList.add(ReservationStatusEnum.RESERVATION_FAIL.getCode());
            params.put("reservationStatusList", reservationStatusList);
            params.put("dayCutProductCodeList", dayCutProductCodes);
            List<String> unMatchReservationProductList = orderInternalService.queryUnReservationSuccessProductListByParams(params);
            if(!CollectionUtils.isNullOrEmpty(unMatchReservationProductList)){
                for(String productCode : unMatchReservationProductList){
                    InterfaceRetryEntity record = new InterfaceRetryEntity();
                    record.setProductCode(productCode);
                    record.setBusinessType(InterfaceRetryBusinessTypeEnum.ASSET_MATCH_NOTIFY_TXS.getCode());
                    int count = interfaceRetryDAO.queryRetryFailureCountByProdCodeForDayCut(record);
                    if(count >0){
                        continue;
                    }
                    productCodeList.add(productCode);
                }
            }
        }

        //请求参数封装
        NotifyUnMatchedOrderReq req1 = new NotifyUnMatchedOrderReq();
        req1.setSerialNo(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYMMDD));
        req1.setProductCodes(productCodeList);
        // 调用远程服务
        logger.info("日切通知唐小僧投资未匹配结果请求参数：" + req1);
        NotifyResp resp1 = txsCallBackService.notifyUnMatchedReservationOrder(req1);
        logger.info("日切通知唐小僧投资未匹配结果响应结果：" + resp1);
        // 判断远程URl调用是否成功
        if (null != resp1 && !"0000".equals(resp1.getCode())) {
            logger.info("日切通知唐小僧投资未匹配结果调用失败:" + resp1.getMsg());
        }


        //请求参数封装
        NotifyUnMatchedOrderReq req = new NotifyUnMatchedOrderReq();
        req.setSerialNo(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYMMDD));
        req.setProductCodes(dayCutProductCodes);
        // 调用远程服务
        logger.info("日切通知唐小僧产品售罄请求参数：" + req);
        NotifyResp resp = txsCallBackService.notifyTxsSoldOutProduct(req);
        logger.info("日切通知唐小僧产品售罄响应结果：" + resp);
        // 判断远程URl调用是否成功
        if (null != resp && !"0000".equals(resp.getCode())) {
            logger.info("日切通知唐小僧产品售罄调用失败:" + resp.getMsg());
        }



        logger.info("UnMatchReservationOrderNotifyTask end running ....");
        return null;
    }

    @Override
    public Comparator<ReservationOrderDTO> getComparator() {
        return null;
    }
}
*/
