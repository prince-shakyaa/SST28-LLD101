package ratelimiter;

public class InternalService {

    private final RateLimiter rateLimiter;
    private final ExternalResourceClient externalClient;

    public InternalService(RateLimiter rateLimiter, ExternalResourceClient externalClient) {
        this.rateLimiter = rateLimiter;
        this.externalClient = externalClient;
    }

    public String handleRequest(String customerId, String requestData, boolean needsExternalCall) {
        System.out.println("  [InternalService] Handling request for customer: " + customerId);

        if (!needsExternalCall) {
            System.out.println("  [InternalService] No external call needed. Serving from cache/logic.");
            return "Served internally for: " + requestData;
        }

        System.out.println("  [InternalService] External call required. Checking rate limiter...");

        if (!rateLimiter.isAllowed(customerId)) {
            System.out.println("  [InternalService] RATE LIMIT EXCEEDED for customer: " + customerId
                    + ". Request rejected gracefully.");
            return "ERROR: Rate limit exceeded for customer " + customerId
                    + ". Please retry after the window resets.";
        }

        System.out.println("  [InternalService] Rate limit OK. Calling external resource...");
        String result = externalClient.call(requestData);
        System.out.println("  [InternalService] External call succeeded.");
        return result;
    }
}
