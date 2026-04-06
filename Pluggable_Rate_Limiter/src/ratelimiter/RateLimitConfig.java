package ratelimiter;

public class RateLimitConfig {

    private final int maxRequests;
    private final long windowDurationMs;

    public RateLimitConfig(int maxRequests, long windowDurationMs) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("maxRequests must be positive");
        }
        if (windowDurationMs <= 0) {
            throw new IllegalArgumentException("windowDurationMs must be positive");
        }
        this.maxRequests = maxRequests;
        this.windowDurationMs = windowDurationMs;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowDurationMs() {
        return windowDurationMs;
    }

    @Override
    public String toString() {
        return "RateLimitConfig{maxRequests=" + maxRequests
                + ", windowDurationMs=" + windowDurationMs + "}";
    }
}
