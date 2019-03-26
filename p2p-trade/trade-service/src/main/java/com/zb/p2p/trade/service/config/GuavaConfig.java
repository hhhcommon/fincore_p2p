package com.zb.p2p.trade.service.config;

import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;

import org.springframework.cache.annotation.EnableCaching;

import org.springframework.cache.guava.GuavaCache;

import org.springframework.cache.support.SimpleCacheManager;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

/**
 * <p>guava缓存配置</p>
 *
 * @author Bruce
 */

@Configuration
@EnableCaching
public class GuavaConfig {

    private static final int DEFAULT_MAXSIZE = 1000000;
    private static final int DEFAULT_TTL = 3600;

    /**
     * 配置缓存
     */
    @Bean
    public CacheManager cacheManager() {

        SimpleCacheManager manager = new SimpleCacheManager();

        // 自定义的cache注册到cacheManager中，GuavaCache实现了org.springframework.cache.Cache接口
        ArrayList<GuavaCache> caches = new ArrayList<>();

        for (Caches c : Caches.values()) {
            caches.add(new GuavaCache(c.name(),
                    CacheBuilder.newBuilder().recordStats().expireAfterWrite(c.getTtl(),
                            TimeUnit.SECONDS).maximumSize(c.getMaxSize()).build()));
        }
        manager.setCaches(caches);

        return manager;

    }

    /**
     * 定义cache名称、超时时长秒、最大个数
     * <p>
     * 每个cache缺省3600秒过期，最大个数100W
     */
    public enum Caches {

        Daily_Income_Cache(360),

        Order_Income_Cache(360),

        Repay_Principle_Interest_Cache(720);

        private int maxSize = DEFAULT_MAXSIZE; //最大數量
        private int ttl = DEFAULT_TTL; //过期时间（秒）

        Caches(int ttl) {
            this.ttl = ttl;
        }

        Caches(int ttl, int maxSize) {
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }
    }

}
