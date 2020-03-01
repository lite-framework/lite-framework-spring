package io.lite.framework.utils;

import io.lite.framework.type.Callback;
import lombok.var;
import org.springframework.data.redis.core.RedisOperations;

import java.util.concurrent.TimeUnit;


public class RedisHelper {

    public static long nextId(RedisOperations redisOperations, String type) {

        // todo cc 这里可以优化为 Lua 脚本，将对Redis的调用变为1
        long time = System.currentTimeMillis();
        String key = type + time;
        long no = redisOperations.opsForValue().increment(key);
        if (redisOperations.getExpire(key) < 0) {

            // rules 分布式id incr的key 3分钟过期（如果redis清空，且时间倒退，则有可能出现重复单号）
            redisOperations.expire(key, 3, TimeUnit.MINUTES);
        }


        if (no > 999) {
            while (System.currentTimeMillis() < time + 1) {
            }
            return nextId(redisOperations, type);
        }

        // 占位策略，理论值和效果和 Blizzard 一致
        return time * 1_00_000 + no;
    }


    public static boolean getLock(RedisOperations redisOperations, String lockKey, int expireTime) {
        var value = redisOperations.opsForValue().setIfAbsent(lockKey, System.currentTimeMillis(), expireTime, TimeUnit.SECONDS);
        return value;
    }


    public static boolean withDistributedLock(RedisOperations redisOperations, String lockKey, Callback callback, int expireTime) {

        if (getLock(redisOperations, lockKey, expireTime)) {

            try {
                callback.exec();
            } catch (Throwable t) {
                throw t;
            } finally {
                redisOperations.delete(lockKey);
            }

            return true;
        }
        return false;
    }


    public static boolean withDistributedConcurrency(RedisOperations redisOperations, String key, Callback callback, int concurrency) {
        return withDistributedConcurrency(redisOperations, key, callback, concurrency, 5);
    }


    public static boolean withDistributedConcurrency(RedisOperations redisOperations, String key, Callback callback, int concurrency, int expireTimeSecond) {

        redisOperations.opsForValue().setIfAbsent(key, 0, expireTimeSecond, TimeUnit.SECONDS);

        if (redisOperations.opsForValue().increment(key) > concurrency) {
            return false;
        }

        callback.exec();

        return true;
    }


}
