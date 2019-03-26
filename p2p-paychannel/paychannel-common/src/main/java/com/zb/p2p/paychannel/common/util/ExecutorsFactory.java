package com.zb.p2p.paychannel.common.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程池
 * e.g: sendMsgPool-thread-1
 * <p>
 * Created by limingxin on 2018/1/18.
 */
public class ExecutorsFactory {
    private final static int initCore = 16;
    private final static int maxCore = initCore * 4;
    private final static int queueSize = 2 << 16;
    private final static String prefix = "thread";
    private final static String separate = "-";

    public static ExecutorService getExecutorService(final String threadPoolName) {
        final AtomicInteger seq = new AtomicInteger(1);
        SecurityManager s = System.getSecurityManager();
        final ThreadGroup group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        return new ThreadPoolExecutor(initCore, maxCore, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(group, r,
                        threadPoolName + separate + prefix + separate + seq.getAndIncrement(), 0);
                if (t.isDaemon())
                    t.setDaemon(false);
                if (t.getPriority() != Thread.NORM_PRIORITY)
                    t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        });
    }
}
