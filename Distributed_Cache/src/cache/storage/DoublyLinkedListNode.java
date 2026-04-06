package cache.storage;

/**
 * A node in a doubly linked list, used internally by the LRU eviction policy
 * to track access order in O(1) time.
 */
public class DoublyLinkedListNode<K> {

    public K key;
    public DoublyLinkedListNode<K> prev;
    public DoublyLinkedListNode<K> next;

    public DoublyLinkedListNode(K key) {
        this.key = key;
    }
}
