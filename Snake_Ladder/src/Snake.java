public class Snake {
    private final int head;
    private final int tail;

    public Snake(int head, int tail) {
        if (head <= tail) throw new IllegalArgumentException("Snake head must be greater than tail.");
        this.head = head;
        this.tail = tail;
    }

    public int getHead() { return head; }
    public int getTail() { return tail; }

    @Override
    public String toString() {
        return "Snake[head=" + head + ", tail=" + tail + "]";
    }
}
