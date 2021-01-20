package app.device.service;

import app.device.exception.RedisLockFailureException;
import core.framework.inject.Inject;
import core.framework.redis.Redis;
import core.framework.util.StopWatch;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * @author beckl
 */
public class RedisService {
    @Inject
    Redis redis;

    /**
     * Providing a way to lock resource
     *
     * @param key      Lock key
     * @param value    Lock value
     * @param lockTime Time to lock the key in redis
     * @param waitTime Time to wait for current thread to acquire the lock
     */
    private void lock(String key, String value, Duration lockTime, Duration waitTime) {
        StopWatch waitTimeWatch = new StopWatch();
        boolean isLockSuccessfully;
        do {
            isLockSuccessfully = redis.set(key, value, lockTime, true);
            if (!isLockSuccessfully) {
                if (waitTime == null || waitTime.isNegative() || waitTime.isZero()) {
                    throw new RedisLockFailureException("Lock failed without any waiting time");
                }
                if (waitTimeWatch.elapsed() >= waitTime.toNanos()) {
                    throw new RedisLockFailureException("Lock failed until the waiting time is ended");
                }
            }
        } while (!isLockSuccessfully);
    }

    /**
     * @param key      Lock key
     * @param value    Lock value
     * @param lockTime Time to lock the key in redis
     * @param waitTime Time to wait for current thread to acquire the lock
     * @param supplier The supplier to execute while current thread acquire the lock
     * @return result returned by callback
     */
    public <T> T lock(String key, String value, Duration lockTime, Duration waitTime, Supplier<T> supplier) {
        lock(key, value, lockTime, waitTime);

        try {
            return supplier.get();
        } finally {
            redis.del(key);
        }
    }

    /**
     * @param key      Lock key
     * @param value    Lock value
     * @param lockTime Time to lock the key in redis
     * @param waitTime Time to wait for current thread to acquire the lock
     * @param runnable The runnable to execute while current thread acquire the lock
     */
    public void lock(String key, String value, Duration lockTime, Duration waitTime, Runnable runnable) {
        lock(key, value, lockTime, waitTime);

        try {
            runnable.run();
        } finally {
            redis.del(key);
        }
    }
}
