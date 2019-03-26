package com.zb.p2p.trade.service.common.impl;

import com.zb.p2p.trade.service.common.DistributedLockService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by limingxin on 2017/9/1.
 */
@Service
public class DistributedLockServiceImpl implements DistributedLockService {
    @Value("${zk.registry.address}")
    private String zookeeperConnectionString;
    @Value("${zk.lock.pool.max}")
    private int maxConn;
    //分布式锁专用zk节点
    @Value("${zk.lock.node}")
    private String lockZkNode;

    private CuratorFramework curatorFramework;

    private ThreadLocal<Map<String, InterProcessMutex>> threadLocalLock = new ThreadLocal<>();

    @PostConstruct
    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        curatorFramework = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        curatorFramework.start();
    }

    @Override
    public void tryLock(String lockKey) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, lockZkNode + lockKey);
        Map<String, InterProcessMutex> lockMap = threadLocalLock.get();
        if (lockMap == null) {
            lockMap = new HashMap<>();
        }
        lockMap.put(lockKey, lock);
        threadLocalLock.set(lockMap);
        lock.acquire();
    }

    @Override
    public boolean tryLock(String lockKey, long timeOut, TimeUnit timeUnit) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, lockZkNode + lockKey);
        if (lock.acquire(timeOut, timeUnit)) {
            Map<String, InterProcessMutex> lockMap = threadLocalLock.get();
            if (lockMap == null) {
                lockMap = new HashMap<>();
            }
            lockMap.put(lockKey, lock);
            threadLocalLock.set(lockMap);
            return true;
        }
        return false;
    }

    @Override
    public void unLock(String lockKey) throws Exception {
        threadLocalLock.get().get(lockKey).release();
    }

    @Override
    public void unLockAndDel(String lockKey) throws Exception {
        threadLocalLock.get().get(lockKey).release();
        threadLocalLock.get().remove(lockKey);
        curatorFramework.delete().forPath(lockZkNode + lockKey);
    }

}
