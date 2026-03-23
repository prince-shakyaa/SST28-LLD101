package com.snakeladder.service;

import java.util.Random;

/**
 * Represents the dice used in the game.
 *
 * <p>Difficulty levels:
 * <ul>
 *   <li><b>EASY</b>: Standard 6-sided dice (1–6)</li>
 *   <li><b>HARD</b>: 12-sided dice (1–12) — faster movement, harder to stop exactly on winning cell</li>
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
     * Rolls the dice and returns a value.
     * EASY: 1–6, HARD: 1–12
     */
    public int roll() {
        int sides = (difficulty == Difficulty.HARD) ? 12 : 6;
        return 1 + random.nextInt(sides);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
