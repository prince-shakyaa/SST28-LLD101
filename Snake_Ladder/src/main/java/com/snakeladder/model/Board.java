package com.snakeladder.model;

import java.util.*;

/**
 * Represents the Snakes and Ladders board.
 *
 * <p>The board is n×n (cells 1..n²). It randomly places exactly n snakes and n ladders
 * such that:
 * <ul>
 *   <li>No snake head equals a ladder start (no overlap)</li>
 *   <li>No two snakes share a head; no two ladders share a start</li>
 *   <li>Snakes and ladders do not create cycles (the destination of a snake/ladder
 *       is never itself a snake head or ladder start)</li>
 *   <li>Cell 1 and cell n² are always free (no snake/ladder starts there)</li>
 * </ul>
 */
public class Board {

    private final int n;          // board dimension
    private final int totalCells; // n * n
    private final Cell[] cells;   // 1-indexed (cells[0] unused)
    private final List<Snake> snakes;
    private final List<Ladder> ladders;

    public Board(int n) {
        this.n = n;
        this.totalCells = n * n;
        this.cells = new Cell[totalCells + 1];
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();

        for (int i = 1; i <= totalCells; i++) {
            cells[i] = new Cell(i);
        }

        placeSnakesAndLadders();
    }

    /**
     * Randomly places n snakes and n ladders without overlap or cycles.
     */
    private void placeSnakesAndLadders() {
        Random random = new Random();
        // Positions already used as a snake-head, ladder-start, or a jump destination
        Set<Integer> occupiedStarts = new HashSet<>();
        Set<Integer> occupiedDestinations = new HashSet<>();

        // Reserve cell 1 (no start) and cell totalCells (no start)
        occupiedStarts.add(1);
        occupiedStarts.add(totalCells);

        // --- Place n Snakes ---
        int snakesPlaced = 0;
        int maxAttempts = 10_000;
        while (snakesPlaced < n && maxAttempts-- > 0) {
            // head must be in [2, totalCells-1] (never cell 1 or last)
            int head = 2 + random.nextInt(totalCells - 2);
            if (occupiedStarts.contains(head)) continue;

            // tail must be less than head and not already a destination
            int tail = 1 + random.nextInt(head - 1);
            if (occupiedDestinations.contains(tail)) continue;
            if (occupiedStarts.contains(tail)) continue; // tail shouldn't be a start itself

            Snake snake = new Snake(head, tail);
            cells[head].setSnake(snake);
            snakes.add(snake);
            occupiedStarts.add(head);
            occupiedDestinations.add(tail);
            snakesPlaced++;
        }

        // --- Place n Ladders ---
        int laddersPlaced = 0;
        maxAttempts = 10_000;
        while (laddersPlaced < n && maxAttempts-- > 0) {
            // start must be in [2, totalCells-1]
            int start = 2 + random.nextInt(totalCells - 2);
            if (occupiedStarts.contains(start)) continue;

            // end must be greater than start and not already a destination
            if (start >= totalCells - 1) continue; // no room above
            int end = (start + 1) + random.nextInt(totalCells - start);
            if (end > totalCells) end = totalCells;
            if (occupiedDestinations.contains(end)) continue;
            if (occupiedStarts.contains(end)) continue;

            Ladder ladder = new Ladder(start, end);
            cells[start].setLadder(ladder);
            ladders.add(ladder);
            occupiedStarts.add(start);
            occupiedDestinations.add(end);
            laddersPlaced++;
        }
    }

    /**
     * Given a position after dice roll, returns the final position after
     * applying any snake or ladder on that cell.
     */
    public int getFinalPosition(int position) {
        if (position < 1 || position > totalCells) return position;
        Cell cell = cells[position];
        if (cell.hasSnake()) {
            System.out.println("  🐍 Oh no! Snake at " + position
                + " → slides down to " + cell.getSnake().getTail());
            return cell.getSnake().getTail();
        }
        if (cell.hasLadder()) {
            System.out.println("  🪜  Ladder at " + position
                + " → climbs up to " + cell.getLadder().getEnd());
            return cell.getLadder().getEnd();
        }
        return position;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public int getN() {
        return n;
    }

    public List<Snake> getSnakes() {
        return Collections.unmodifiableList(snakes);
    }

    public List<Ladder> getLadders() {
        return Collections.unmodifiableList(ladders);
    }

    /**
     * Prints a pretty layout of snakes and ladders.
     */
    public void printLayout() {
        System.out.println("\n📋 Board Layout (" + n + "×" + n + " = " + totalCells + " cells)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🐍 Snakes (" + snakes.size() + "):");
        snakes.forEach(s -> System.out.println("   Head: " + s.getHead() + "  →  Tail: " + s.getTail()));
        System.out.println("🪜  Ladders (" + ladders.size() + "):");
        ladders.forEach(l -> System.out.println("   Start: " + l.getStart() + "  →  End: " + l.getEnd()));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
}
