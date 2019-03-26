package com.zb.p2p.paychannel.service.commonservice;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁服务
 * Created by limingxin on 2017/9/1.
 */
public interface DistributedLockService {

    void tryLock(String lockKey) throws Exception;

    boolean tryLock(String lockKey, long timeOut, TimeUnit timeUnit) throws Exception;

    void unLock(String lockKey) throws Exception;
}
