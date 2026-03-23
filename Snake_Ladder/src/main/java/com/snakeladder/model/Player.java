package com.snakeladder.model;

/**
 * Represents a player in the Snakes and Ladders game.
 * Each player has a name and a current position on the board.
 * Position 0 means the player is off the board (initial state).
 */
public class Player {

    private final String name;
    private int position;  // 0 = off-board

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player[name=" + name + ", position=" + position + "]";
    }
}
