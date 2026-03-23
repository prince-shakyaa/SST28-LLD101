package com.snakeladder.model;

/**
 * Represents a single cell on the Snakes and Ladders board.
 * A cell may have a snake head, ladder start, or be a normal cell.
 */
public class Cell {

    private final int cellNumber;
    private Snake snake;    // null if no snake head here
    private Ladder ladder;  // null if no ladder start here

    public Cell(int cellNumber) {
        this.cellNumber = cellNumber;
        this.snake = null;
        this.ladder = null;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public boolean hasSnake() {
        return snake != null;
    }

    public boolean hasLadder() {
        return ladder != null;
    }

    @Override
    public String toString() {
        return "Cell[" + cellNumber + "]";
    }
}
