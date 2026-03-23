package com.snakeladder.model;

/**
 * Represents a Snake on the board.
 * A snake has a head (higher position) and a tail (lower position).
 * When a player lands on the head, they slide down to the tail.
 */
public class Snake {

    private final int head;  // higher position
    private final int tail;  // lower position

    public Snake(int head, int tail) {
        if (head <= tail) {
            throw new IllegalArgumentException(
                "Snake head (" + head + ") must be greater than its tail (" + tail + ")");
        }
        this.head = head;
        this.tail = tail;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return "Snake[head=" + head + ", tail=" + tail + "]";
    }
}
