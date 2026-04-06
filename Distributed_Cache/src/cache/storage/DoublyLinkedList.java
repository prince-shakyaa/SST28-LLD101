package cache.storage;

/**
 * A doubly linked list used by the LRU eviction policy.
 * - Most recently accessed keys are at the tail (end).
 * - Least recently accessed keys are at the head (front).
 * All operations run in O(1) time.
 */
public class DoublyLinkedList<K> {

    private DoublyLinkedListNode<K> dummyHead;
    private DoublyLinkedListNode<K> dummyTail;

    public DoublyLinkedList() {
        dummyHead = new DoublyLinkedListNode<>(null);
        dummyTail = new DoublyLinkedListNode<>(null);
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    /**
     * Adds a new node at the tail (marks it as most recently used).
     */
    public DoublyLinkedListNode<K> addNodeAtTail(K key) {
        DoublyLinkedListNode<K> newNode = new DoublyLinkedListNode<>(key);
        DoublyLinkedListNode<K> prevNode = dummyTail.prev;

        prevNode.next = newNode;
        newNode.prev = prevNode;
        newNode.next = dummyTail;
        dummyTail.prev = newNode;

        return newNode;
    }

    /**
     * Detaches a node from its current position in the list.
     */
    public void detachNode(DoublyLinkedListNode<K> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    /**
     * Moves an existing node to the tail (marks it as most recently used).
     */
    public void moveNodeToTail(DoublyLinkedListNode<K> node) {
        detachNode(node);

        DoublyLinkedListNode<K> prevNode = dummyTail.prev;
        prevNode.next = node;
        node.prev = prevNode;
        node.next = dummyTail;
        dummyTail.prev = node;
    }

    /**
     * Returns the key of the node at the head (least recently used).
     * Returns null if the list is empty.
     */
    public K getKeyAtHead() {
        if (dummyHead.next == dummyTail) {
            return null; // list is empty
        }
        return dummyHead.next.key;
    }

    /**
     * Removes and returns the node at the head (the LRU candidate).
     */
    public DoublyLinkedListNode<K> removeNodeFromHead() {
        if (dummyHead.next == dummyTail) {
            return null;
        }
        DoublyLinkedListNode<K> headNode = dummyHead.next;
        detachNode(headNode);
        return headNode;
    }
}
