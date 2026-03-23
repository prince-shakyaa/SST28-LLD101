package com.snakeladder.model;

/**
 * Represents a Ladder on the board.
 * A ladder has a start (lower position) and an end (higher position).
 * When a player lands on the start, they climb up to the end.
 */
public class Ladder {

    private final int start;  // lower position
    private final int end;    // higher position

    public Ladder(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException(
                "Ladder start (" + start + ") must be less than its end (" + end + ")");
        }
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Ladder[start=" + start + ", end=" + end + "]";
    }
}
