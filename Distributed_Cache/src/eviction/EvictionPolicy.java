package eviction;

/**
 * Interface representing a pluggable eviction policy for a cache node.
 * Implementations can define different eviction strategies such as
 * LRU (Least Recently Used), MRU (Most Recently Used), LFU (Least Frequently Used), etc.
 */
public interface EvictionPolicy<K> {

    /**
     * Called when a key is accessed (get or put). Used to track recency/frequency.
     *
     * @param key the key that was accessed
     */
    void keyAccessed(K key);

    /**
     * Returns the key that should be evicted according to this policy.
     *
     * @return the key to evict
     */
    K evict();
}
