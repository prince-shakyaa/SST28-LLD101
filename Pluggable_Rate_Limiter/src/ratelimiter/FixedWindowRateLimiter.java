package ratelimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter {

    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> windowStartTimes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> keyLocks = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(RateLimitConfig config) {
        this.config = config;
    }

    @Override
    public boolean isAllowed(String key) {
        Object lock = keyLocks.computeIfAbsent(key, k -> new Object());

        synchronized (lock) {
            long now = System.currentTimeMillis();
            long windowStart = windowStartTimes.getOrDefault(key, now);
            boolean windowExpired = (now - windowStart) >= config.getWindowDurationMs();

            if (windowExpired) {
                windowStartTimes.put(key, now);
                counters.put(key, new AtomicInteger(0));
            }

            AtomicInteger counter = counters.computeIfAbsent(key, k -> new AtomicInteger(0));

            if (counter.get() < config.getMaxRequests()) {
                counter.incrementAndGet();
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
