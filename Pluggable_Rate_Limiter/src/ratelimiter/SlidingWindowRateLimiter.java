package ratelimiter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, Deque<Long>> requestTimestamps = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> keyLocks = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public boolean isAllowed(String key) {
        Object lock = keyLocks.computeIfAbsent(key, k -> new Object());

        synchronized (lock) {
            long now = System.currentTimeMillis();
            long windowStart = now - config.getWindowDurationMs();

            Deque<Long> timestamps = requestTimestamps.computeIfAbsent(key, k -> new ArrayDeque<>());

            Iterator<Long> iterator = timestamps.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() <= windowStart) {
                    iterator.remove();
                } else {
                    break;
                }
            }

            if (timestamps.size() < config.getMaxRequests()) {
                timestamps.addLast(now);
                return true;
            }

            return false;
        }
    }

    @Override
    public RateLimitConfig getConfig() {
        return config;
    }
}
