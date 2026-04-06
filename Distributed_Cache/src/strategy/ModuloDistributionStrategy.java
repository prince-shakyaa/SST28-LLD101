package strategy;

/**
 * Modulo-based distribution strategy.
 *
 * How it works:
 * - Computes: Math.abs(key.hashCode()) % numberOfNodes
 * - This maps every key deterministically to one of the available cache nodes.
 *
 * Limitations (and when to switch):
 * - When a node is added or removed, up to (N-1)/N keys may be remapped —
 *   this is why consistent hashing is preferred for dynamic node clusters.
 * - For a static, fixed-size node cluster this strategy is simple and effective.
 */
public class ModuloDistributionStrategy implements DistributionStrategy {

    @Override
    public int getNodeIndex(String key, int numberOfNodes) {
        return Math.abs(key.hashCode()) % numberOfNodes;
    }
}
