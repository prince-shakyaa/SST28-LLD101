package com.snakeladder.service;

import java.util.Random;

/**
 * Represents the dice used in the game.
 *
 * <p>Both difficulty levels use a standard 6-sided dice (1–6).
 * The difference is in how consecutive sixes are handled by the Game:
 * <ul>
 *   <li><b>EASY</b>: Consecutive sixes are always valid — roll again every time you get a 6.</li>
 *   <li><b>HARD</b>: 1 or 2 consecutive sixes are valid (extra rolls), but 3 consecutive
 *       sixes invalidates the entire turn and the player returns to their starting position.</li>
 * </ul>
 */
public class Dice {

    public enum Difficulty {
        EASY, HARD
    }

    private final Difficulty difficulty;
    private final Random random;

    public Dice(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.random = new Random();
    }

    /**
     * Rolls the dice and returns a value between 1 and 6 (inclusive).
     */
    public int roll() {
        return 1 + random.nextInt(6);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
