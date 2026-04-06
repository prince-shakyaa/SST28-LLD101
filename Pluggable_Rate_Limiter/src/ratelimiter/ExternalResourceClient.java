package ratelimiter;

public class ExternalResourceClient {

    private final String resourceName;

    public ExternalResourceClient(String resourceName) {
        this.resourceName = resourceName;
    }

    public String call(String requestData) {
        System.out.println("    [ExternalResource] Calling " + resourceName + " with data: " + requestData);
        return "Response from " + resourceName + " for: " + requestData;
    }
}
