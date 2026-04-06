package eviction;

import cache.storage.DoublyLinkedList;
import cache.storage.DoublyLinkedListNode;
import java.util.HashMap;
import java.util.Map;

/**
 * LRU (Least Recently Used) eviction policy implementation.
 *
 * How it works:
 * - A doubly linked list maintains the access order of keys.
 * - The tail holds the most recently used key; the head holds the least recently used key.
 * - A HashMap maps each key to its corresponding linked list node for O(1) access.
 *
 * On keyAccessed(key):
 *   - If the key already exists, move its node to the tail.
 *   - Otherwise, create a new node at the tail.
 *
 * On evict():
 *   - Remove and return the key at the head (the LRU key).
 */
public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {

    private final DoublyLinkedList<K> accessOrderList;
    private final Map<K, DoublyLinkedListNode<K>> nodeMap;

    public LRUEvictionPolicy() {
        this.accessOrderList = new DoublyLinkedList<>();
        this.nodeMap = new HashMap<>();
    }

    @Override
    public void keyAccessed(K key) {
        if (nodeMap.containsKey(key)) {
            // Key already tracked — move to tail (most recently used)
            accessOrderList.moveNodeToTail(nodeMap.get(key));
        } else {
            // New key — add at tail
            DoublyLinkedListNode<K> newNode = accessOrderList.addNodeAtTail(key);
            nodeMap.put(key, newNode);
        }
    }

    @Override
    public K evict() {
        DoublyLinkedListNode<K> lruNode = accessOrderList.removeNodeFromHead();
        if (lruNode == null) {
            throw new IllegalStateException("Eviction policy is empty. Nothing to evict.");
        }
        nodeMap.remove(lruNode.key);
        return lruNode.key;
    }
}
