package com.zb.p2p.trade.service.common;

import com.zb.p2p.trade.common.enums.AppCodeEnum;
import com.zb.p2p.trade.common.exception.BusinessException;
import com.zb.p2p.trade.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static String redisCode = "utf-8";

    /**
     * 主动删除缓存
     * @param keys
     */
    public long del(final String... keys) {
        return (long) redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public long set(final byte[] key, final byte[] value, final long liveTime) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 可防止重复执行
     * @param key
     * @param value
     * @param liveTime
     */
    public long setnx(final byte[] key, final byte[] value, final long liveTime) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setNX(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 放入缓存
     * @param key
     * @param value
     * @param liveTime
     */
    public long set(String key, String value, long liveTime) {
        long r = 0;
        if (StringUtils.isBlank(key)) {
            throw new BusinessException(AppCodeEnum._9999_ERROR.getMessage());
        }
        try {
            r = this.set(key.getBytes(), value.getBytes(redisCode), liveTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * @param key
     * @param value
     */
    public long setnx(String key, String value) {
        long r = 0;
        if (StringUtils.isBlank(key)) {
            throw new BusinessException(AppCodeEnum._9999_ERROR.getMessage());
        }
        try {
            r = this.setnx(key.getBytes(), value.getBytes(redisCode), -1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * @param key
     * @param value
     */
    public long set(String key, String value) {
        return this.set(key, value, 0L);
    }

    /**
     * @param key
     * @param value
     */
    public long set(byte[] key, byte[] value) {
        return this.set(key, value, 0L);
    }

    /**
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bts = connection.get(key.getBytes());
                    if (bts == null) {
                        return "";
                    }
                    return new String(bts, redisCode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    /**
     * 是否存在
     * @param key
     * @return
     */
    public Boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * @return
     */
    public String flushDB() {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * @return
     */
    public Long dbSize() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     */
    public String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {

                return connection.ping();
            }
        });
    }

    /**
     * 加锁用
     *
     * @param key
     * @param value
     * @param liveTime
     * @return 如果"key"存在返回false；如果"key"不存在，就存入reidis返回true，过期时间为秒
     */
    public boolean setIfAbsent(String key, String value, long liveTime) {
        boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (result) {
            return redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
        } else {
            return false;
        }

    }

    /**
     * @param key
     * @param stepNumber
     * @param liveTime
     */
    public long increment(String key, long stepNumber, long liveTime) {
        long result = 0;
        Long r = this.redisTemplate.opsForValue().increment(key, stepNumber);
        if (r == 1L) {
            Boolean expire = this.redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
            result = expire ? 1 : 0;
        }
        return result;
    }

    /**
     * 存在则返回
     * @param cacheKey
     * @return
     */
    public String existedAndGet(String cacheKey) {
        if (exists(cacheKey)) {
            String cacheResult = get(cacheKey);
            log.info("查询CacheKey=[{}]的数据结果缓存为=[{}]", cacheKey, cacheResult);
            return cacheResult;
        }
        return null;
    }

}


