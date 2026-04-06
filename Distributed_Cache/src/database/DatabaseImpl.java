package database;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory mock implementation of the Database interface.
 * Simulates a persistent backing store for this LLD exercise.
 * Pre-populated with sample data to demonstrate cache miss handling.
 */
public class DatabaseImpl implements Database {

    private final Map<String, String> store;

    public DatabaseImpl() {
        this.store = new HashMap<>();
        // Pre-populate with sample records
        store.put("user:1", "Alice");
        store.put("user:2", "Bob");
        store.put("user:3", "Charlie");
        store.put("product:101", "Laptop");
        store.put("product:102", "Keyboard");
        store.put("product:103", "Monitor");
        store.put("order:501", "Order-A");
        store.put("order:502", "Order-B");
    }

    @Override
    public String get(String key) {
        System.out.println("[DB] Fetching key='" + key + "' from database.");
        return store.get(key);
    }

    @Override
    public void put(String key, String value) {
        System.out.println("[DB] Writing key='" + key + "', value='" + value + "' to database.");
        store.put(key, value);
    }
}
