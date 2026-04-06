import cache.DistributedCache;
import database.Database;
import database.DatabaseImpl;
import strategy.DistributionStrategy;
import strategy.ModuloDistributionStrategy;

/**
 * Entry point demonstrating the Distributed Cache system.
 *
 * Scenarios covered:
 * 1. Cache MISS  → key fetched from DB, stored in cache.
 * 2. Cache HIT   → key served directly from cache node.
 * 3. Cache PUT   → key written to cache and DB.
 * 4. Eviction    → node fills to capacity, LRU key is evicted on next insert.
 * 5. Cluster status summary printed after operations.
 */
public class Main {

    public static void main(String[] args) {

        // --- Setup ---
        Database database = new DatabaseImpl();
        DistributionStrategy strategy = new ModuloDistributionStrategy();

        // 3 cache nodes, each with capacity for 3 keys
        DistributedCache cache = new DistributedCache(3, 3, strategy, database);

        System.out.println("\n======== Scenario 1: Cache MISS (key in DB) ========");
        System.out.println("get('user:1') = " + cache.get("user:1"));   // miss → DB → cache
        System.out.println("get('user:2') = " + cache.get("user:2"));
        System.out.println("get('product:101') = " + cache.get("product:101"));

        System.out.println("\n======== Scenario 2: Cache HIT ========");
        System.out.println("get('user:1') = " + cache.get("user:1"));   // hit
        System.out.println("get('product:101') = " + cache.get("product:101")); // hit

        System.out.println("\n======== Scenario 3: PUT new value ========");
        cache.put("session:abc", "SessionData-XYZ");
        System.out.println("get('session:abc') = " + cache.get("session:abc")); // hit

        System.out.println("\n======== Scenario 4: LRU Eviction ========");
        // Fill up whatever node user:1 and user:2 land on, then trigger eviction
        cache.put("temp:k1", "val1");
        cache.put("temp:k2", "val2");
        cache.put("temp:k3", "val3");
        cache.put("temp:k4", "val4");  // one of the earlier keys will be evicted

        System.out.println("\n======== Scenario 5: Key NOT in DB ========");
        System.out.println("get('unknown:key') = " + cache.get("unknown:key")); // not found anywhere

        cache.printClusterStatus();
    }
}
