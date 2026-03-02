package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe, lazy-initialised Singleton using double-checked locking.
 *
 * Guarantees:
 * 1. Only one instance per JVM run (thread-safe via volatile + DCL).
 * 2. Reflection cannot create a second instance (constructor guard).
 * 3. Serialization returns the same singleton (readResolve).
 */
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // volatile ensures the write to INSTANCE in one thread is
    // visible to all other threads immediately (no stale cache).
    private static volatile MetricsRegistry INSTANCE;

    private final Map<String, Long> counters = new HashMap<>();

    /** Private constructor — blocks normal and reflection-based construction. */
    private MetricsRegistry() {
        if (INSTANCE != null) {
            throw new IllegalStateException(
                    "MetricsRegistry already initialised — use getInstance().");
        }
    }

    /**
     * Double-checked locking: only synchronises on the first call.
     * Subsequent calls read the volatile field without synchronisation cost.
     */
    public static MetricsRegistry getInstance() {
        if (INSTANCE == null) {
            synchronized (MetricsRegistry.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MetricsRegistry();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    /**
     * Ensures that deserialisation returns the same singleton instance
     * instead of creating a new object.
     */
    @Serial
    protected Object readResolve() {
        return getInstance();
    }
}
