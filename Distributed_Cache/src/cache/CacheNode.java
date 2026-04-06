package cache;

import eviction.EvictionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single cache node in the distributed cache cluster.
 *
 * Each CacheNode:
 * - Has a fixed maximum capacity.
 * - Maintains its own key-value store (in-memory HashMap).
 * - Uses an injected EvictionPolicy to decide which key to evict when capacity is full.
 *
 * Eviction Flow:
 * - When the node is at capacity and a new key needs to be stored,
 *   the eviction policy picks the key to remove before inserting the new one.
 *
 * Extensibility:
 * - The EvictionPolicy is injected via the constructor, so switching from LRU to
 *   LFU or MRU requires no changes to CacheNode itself.
 */
public class CacheNode {

    private final int capacity;
    private final Map<String, String> store;
    private final EvictionPolicy<String> evictionPolicy;
    private final int nodeId;

    public CacheNode(int nodeId, int capacity, EvictionPolicy<String> evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.store = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    /**
     * Retrieves the value for the given key from this node.
     * Returns null if the key is not present.
     * Also notifies the eviction policy of this access.
     */
    public String get(String key) {
        if (!store.containsKey(key)) {
            return null;
        }
        evictionPolicy.keyAccessed(key);
        return store.get(key);
    }

    /**
     * Stores the key-value pair in this node.
     * If the node is at capacity, the eviction policy evicts a key first.
     */
    public void put(String key, String value) {
        if (!store.containsKey(key) && store.size() >= capacity) {
            evict();
        }
        store.put(key, value);
        evictionPolicy.keyAccessed(key);
        System.out.println("[Node-" + nodeId + "] Stored key='" + key + "', value='" + value + "'. " +
                "(" + store.size() + "/" + capacity + " slots used)");
    }

    /**
     * Evicts the key chosen by the eviction policy.
     */
    private void evict() {
        String evictedKey = evictionPolicy.evict();
        store.remove(evictedKey);
        System.out.println("[Node-" + nodeId + "] Evicted key='" + evictedKey + "' (LRU eviction).");
    }

    /**
     * Returns whether this node contains the given key.
     */
    public boolean containsKey(String key) {
        return store.containsKey(key);
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getSize() {
        return store.size();
    }

    public int getCapacity() {
        return capacity;
    }
}
