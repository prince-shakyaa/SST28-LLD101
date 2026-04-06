package database;

/**
 * Interface representing the backing database.
 * In a real distributed system this would communicate with an actual DB.
 * For this LLD exercise, it is an in-memory mock.
 */
public interface Database {

    /**
     * Fetches the value for the given key from the database.
     *
     * @param key the key to look up
     * @return the value, or null if not found
     */
    String get(String key);

    /**
     * Writes the key-value pair to the database.
     *
     * @param key   the key
     * @param value the value
     */
    void put(String key, String value);
}
