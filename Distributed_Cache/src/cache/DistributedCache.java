package cache;

import database.Database;
import eviction.LRUEvictionPolicy;
import strategy.DistributionStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * DistributedCache is the central coordinator of the cache cluster.
 *
 * Architecture:
 * - Maintains a list of CacheNode instances.
 * - Uses a pluggable DistributionStrategy to route each key to the correct node.
 * - On a cache miss (get), falls back to the Database and populates the cache.
 * - On a put, writes to the appropriate cache node AND the database.
 *
 * Extensibility:
 * - DistributionStrategy is injected → swap modulo for consistent hashing with no
 *   changes to this class.
 * - EvictionPolicy is injected into each CacheNode → LRU can be swapped for LFU/MRU.
 * - Database is injected → mock can be replaced with a real DB client.
 *
 * Data Flow — get(key):
 *   1. Determine target node via distribution strategy.
 *   2. Check node for key → CACHE HIT → return value.
 *   3. CACHE MISS → query DB → store in node → return value.
 *
 * Data Flow — put(key, value):
 *   1. Determine target node via distribution strategy.
 *   2. Write to cache node (evict if full).
 *   3. Write to database.
 */
public class DistributedCache {

    private final List<CacheNode> nodes;
    private final DistributionStrategy distributionStrategy;
    private final Database database;

    /**
     * Constructs the distributed cache.
     *
     * @param numberOfNodes       number of cache nodes in the cluster
     * @param capacityPerNode     maximum keys each node can hold
     * @param distributionStrategy strategy to route keys to nodes
     * @param database            backing database for cache misses
     */
    public DistributedCache(int numberOfNodes,
                            int capacityPerNode,
                            DistributionStrategy distributionStrategy,
                            Database database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode(i, capacityPerNode, new LRUEvictionPolicy<>()));
        }

        System.out.println("[Cache] Initialized with " + numberOfNodes +
                " node(s), capacity=" + capacityPerNode + " each.");
    }

    /**
     * Retrieves the value for the given key.
     *
     * Steps:
     * 1. Route key to a node via distribution strategy.
     * 2. If node has the key → CACHE HIT.
     * 3. If not → CACHE MISS → fetch from DB → store in cache → return.
     *
     * @param key the cache key
     * @return the value, or null if not found in cache or DB
     */
    public String get(String key) {
        CacheNode targetNode = getTargetNode(key);

        String value = targetNode.get(key);
        if (value != null) {
            System.out.println("[Cache] HIT  — key='" + key + "' found on Node-" + targetNode.getNodeId());
            return value;
        }

        System.out.println("[Cache] MISS — key='" + key + "' not on Node-" + targetNode.getNodeId() + ". Fetching from DB...");
        value = database.get(key);

        if (value != null) {
            targetNode.put(key, value);
        } else {
            System.out.println("[Cache] key='" + key + "' not found in database either.");
        }

        return value;
    }

    /**
     * Stores the key-value pair in the appropriate cache node and the database.
     *
     * @param key   the cache key
     * @param value the value to store
     */
    public void put(String key, String value) {
        CacheNode targetNode = getTargetNode(key);
        targetNode.put(key, value);
        database.put(key, value);
        System.out.println("[Cache] PUT  — key='" + key + "' routed to Node-" + targetNode.getNodeId());
    }

    /**
     * Resolves the target CacheNode for a given key using the distribution strategy.
     */
    private CacheNode getTargetNode(String key) {
        int index = distributionStrategy.getNodeIndex(key, nodes.size());
        return nodes.get(index);
    }

    /**
     * Prints a summary of all nodes' current usage.
     */
    public void printClusterStatus() {
        System.out.println("\n========== Cluster Status ==========");
        for (CacheNode node : nodes) {
            System.out.println("  Node-" + node.getNodeId() +
                    " | Used: " + node.getSize() + "/" + node.getCapacity());
        }
        System.out.println("=====================================\n");
    }
}
