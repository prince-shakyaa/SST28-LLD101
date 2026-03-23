package com.snakeladder.service;

import com.snakeladder.model.Board;
import com.snakeladder.model.Player;

import java.util.*;

/**
 * Manages the Snakes and Ladders game loop.
 *
 * <p>Rules:
 * <ul>
 *   <li>Players take turns in order; eliminated players are skipped.</li>
 *   <li>A player wins when they reach exactly totalCells (or land on it).</li>
 *   <li>If a dice roll would move a player beyond totalCells, the player does not move.</li>
 *   <li>The game ends when only 1 (or 0) active player(s) remain.</li>
 * </ul>
 */
public class Game {

    private final Board board;
    private final Dice dice;
    private final List<Player> players;        // all players (including winners)
    private final Queue<Player> activePlayers; // players still in the game
    private final List<Player> winners;        // ordered list of winners

    public Game(Board board, Dice dice, List<Player> players) {
        this.board = board;
        this.dice = dice;
        this.players = new ArrayList<>(players);
        this.activePlayers = new LinkedList<>(players);
        this.winners = new ArrayList<>();
    }

    /**
     * Starts and runs the game until ≤1 player remains.
     */
    public void start() {
        System.out.println("\n🎮 Game Started! Difficulty: " + dice.getDifficulty());
        System.out.println("Players: " + players.stream()
            .map(Player::getName).reduce((a, b) -> a + ", " + b).orElse(""));
        board.printLayout();

        int turnNumber = 0;

        while (activePlayers.size() > 1) {
            turnNumber++;
            Player current = activePlayers.poll();

            System.out.println("── Turn " + turnNumber + " ─ " + current.getName()
                + " (at position " + current.getPosition() + ") ──");

            int initialPosition = current.getPosition();
            int consecutiveMaxRolls = 0;
            boolean turnContinues = true;
            boolean won = false;
            int maxDiceValue = (dice.getDifficulty() == Dice.Difficulty.HARD) ? 12 : 6;

            while (turnContinues) {
                int diceValue = dice.roll();
                System.out.println("  🎲 Rolled: " + diceValue);

                if (diceValue == maxDiceValue) {
                    consecutiveMaxRolls++;
                    if (consecutiveMaxRolls == 3) {
                        System.out.println("  🚫 Rolled 3 consecutive " + maxDiceValue + "s! Turn is invalid. Returning to position " + initialPosition);
                        current.setPosition(initialPosition);
                        break;
                    }
                    System.out.println("  🌟 Rolled a " + maxDiceValue + "! You get an extra roll.");
                } else {
                    turnContinues = false;
                }

                int newPosition = current.getPosition() + diceValue;

                // Rule: do not move if new position would exceed totalCells
                if (newPosition > board.getTotalCells()) {
                    System.out.println("  ✋ Cannot move! " + newPosition + " > " + board.getTotalCells()
                        + ". " + current.getName() + " stays at " + current.getPosition());
                    continue;
                }

                // Apply move
                int finalPosition = board.getFinalPosition(newPosition);
                current.setPosition(finalPosition);
                System.out.println("  ➡️  " + current.getName() + " moved to position " + finalPosition);

                // Check win
                if (finalPosition == board.getTotalCells()) {
                    won = true;
                    break;
                }
            }

            if (won) {
                winners.add(current);
                int rank = winners.size();
                System.out.println("\n🏆 " + current.getName() + " has WON! (Rank #" + rank + ")\n");
                // player is NOT added back to the queue → eliminated
            } else {
                activePlayers.offer(current); // back to queue
            }
        }

        // Game over
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("          🎉 GAME OVER 🎉");
        System.out.println("═══════════════════════════════════════");

        // If exactly 1 player remains, they are last
        if (!activePlayers.isEmpty()) {
            Player lastPlayer = activePlayers.poll();
            System.out.println("Game ended — only 1 player remains: " + lastPlayer.getName());
        }

        if (!winners.isEmpty()) {
            System.out.println("\n🏅 Final Leaderboard:");
            for (int i = 0; i < winners.size(); i++) {
                System.out.println("  #" + (i + 1) + " → " + winners.get(i).getName());
            }
        }
        System.out.println("═══════════════════════════════════════\n");
    }

    public List<Player> getWinners() {
        return Collections.unmodifiableList(winners);
    }
}
