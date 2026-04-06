package ratelimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("========================================================");
        System.out.println("  PLUGGABLE RATE LIMITER SYSTEM DEMO");
        System.out.println("========================================================\n");

        RateLimitConfig config = new RateLimitConfig(5, 60_000);

        ExternalResourceClient externalClient = new ExternalResourceClient("PaidAnalyticsAPI");

        System.out.println("--------------------------------------------------------");
        System.out.println("  DEMO 1: Fixed Window Rate Limiter (5 req/min for T1)");
        System.out.println("--------------------------------------------------------\n");

        RateLimiter fixedWindowLimiter = RateLimiterFactory.create(AlgorithmType.FIXED_WINDOW, config);
        InternalService serviceWithFixed = new InternalService(fixedWindowLimiter, externalClient);

        runDemo(serviceWithFixed, "T1");

        System.out.println("\n--------------------------------------------------------");
        System.out.println("  DEMO 2: Same business logic, switched to Sliding Window");
        System.out.println("         (No change in InternalService or caller code!)");
        System.out.println("--------------------------------------------------------\n");

        RateLimiter slidingWindowLimiter = RateLimiterFactory.create(AlgorithmType.SLIDING_WINDOW, config);
        InternalService serviceWithSliding = new InternalService(slidingWindowLimiter, externalClient);

        runDemo(serviceWithSliding, "T1");

        System.out.println("\n--------------------------------------------------------");
        System.out.println("  DEMO 3: Multi-tenant rate limiting (T1, T2 independent)");
        System.out.println("         Using Sliding Window");
        System.out.println("--------------------------------------------------------\n");

        RateLimiter sharedLimiter = RateLimiterFactory.create(AlgorithmType.SLIDING_WINDOW,
                new RateLimitConfig(3, 60_000));
        InternalService sharedService = new InternalService(sharedLimiter, externalClient);

        System.out.println("  >> T1 making 4 requests:");
        for (int i = 1; i <= 4; i++) {
            System.out.println("  Request " + i + " [T1]:");
            String result = sharedService.handleRequest("T1", "data-T1-" + i, true);
            System.out.println("  Result: " + result + "\n");
        }

        System.out.println("  >> T2 making 4 requests (independent quota):");
        for (int i = 1; i <= 4; i++) {
            System.out.println("  Request " + i + " [T2]:");
            String result = sharedService.handleRequest("T2", "data-T2-" + i, true);
            System.out.println("  Result: " + result + "\n");
        }

        System.out.println("\n--------------------------------------------------------");
        System.out.println("  DEMO 4: Thread-safety test — 8 concurrent threads for T1");
        System.out.println("         Limit: 5 req/min (Fixed Window)");
        System.out.println("--------------------------------------------------------\n");

        RateLimiter threadSafeLimiter = RateLimiterFactory.create(AlgorithmType.FIXED_WINDOW,
                new RateLimitConfig(5, 60_000));
        InternalService threadSafeService = new InternalService(threadSafeLimiter, externalClient);

        ExecutorService executor = Executors.newFixedThreadPool(8);

        for (int i = 1; i <= 8; i++) {
            final int requestNo = i;
            executor.submit(() -> {
                String result = threadSafeService.handleRequest("T1", "concurrent-data-" + requestNo, true);
                System.out.println("  Thread " + requestNo + " -> " + result);
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("\n========================================================");
        System.out.println("  DEMO COMPLETE");
        System.out.println("========================================================");
    }

    private static void runDemo(InternalService service, String customerId) {
        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + ":");

            boolean needsExternalCall = (i % 2 != 0);

            System.out.println("  needsExternalCall = " + needsExternalCall);
            String result = service.handleRequest(customerId, "payload-" + i, needsExternalCall);
            System.out.println("  Final Result: " + result + "\n");
        }
    }
}
