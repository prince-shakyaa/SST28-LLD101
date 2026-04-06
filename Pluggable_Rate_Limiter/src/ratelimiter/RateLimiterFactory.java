package ratelimiter;

public class RateLimiterFactory {

    public static RateLimiter create(AlgorithmType type, RateLimitConfig config) {
        switch (type) {
            case FIXED_WINDOW:
                return new FixedWindowRateLimiter(config);
            case SLIDING_WINDOW:
                return new SlidingWindowRateLimiter(config);
            case TOKEN_BUCKET:
                throw new UnsupportedOperationException("Token Bucket not yet implemented");
            case LEAKY_BUCKET:
                throw new UnsupportedOperationException("Leaky Bucket not yet implemented");
            case SLIDING_LOG:
                throw new UnsupportedOperationException("Sliding Log not yet implemented");
            default:
                throw new IllegalArgumentException("Unknown algorithm type: " + type);
        }
    }

    private RateLimiterFactory() {
    }
}
