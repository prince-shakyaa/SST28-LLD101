package strategy;

/**
 * Interface representing a pluggable distribution strategy for the distributed cache.
 * Implementations define how a key is mapped to a specific cache node index.
 *
 * Examples of strategies:
 * - Modulo-based: hash(key) % numberOfNodes
 * - Consistent Hashing: distributes keys on a virtual ring for better load balancing
 */
public interface DistributionStrategy {

    /**
     * Determines which cache node index should store the given key.
     *
     * @param key            the cache key
     * @param numberOfNodes  total number of cache nodes available
     * @return               index (0-based) of the cache node responsible for this key
     */
    int getNodeIndex(String key, int numberOfNodes);
}
