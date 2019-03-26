package com.zb.p2p.trade.service.common;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁服务
 * Created by limingxin on 2017/9/1.
 */
public interface DistributedLockService {

    void tryLock(String lockKey) throws Exception;

    boolean tryLock(String lockKey, long timeOut, TimeUnit timeUnit) throws Exception;

    void unLock(String lockKey) throws Exception;

    /**
     * 释放锁并且删除节点
     *
     * @param lockKey
     * @throws Exception
     */
    void unLockAndDel(String lockKey) throws Exception;
}
