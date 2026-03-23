package com.snakeladder.service;

import com.snakeladder.model.Board;
import com.snakeladder.model.Player;

import java.util.*;

/**
 * Manages the Snakes and Ladders game loop.
 *
 * <p>Consecutive Sixes Rules (both modes roll a standard 6-sided dice):
 * <ul>
 *   <li><b>EASY</b>: Every 6 grants a bonus roll. Consecutive sixes are unlimited — always valid.</li>
 *   <li><b>HARD</b>: Every 6 grants a bonus roll. Up to 2 consecutive sixes are valid.
 *       If a 3rd consecutive 6 is rolled, the entire turn is INVALIDATED:
 *       the player returns to the position they were at before the turn started.</li>
 * </ul>
 *
 * <p>Other Rules:
 * <ul>
 *   <li>Players take turns in order; eliminated (won) players are skipped.</li>
 *   <li>A player wins when they land exactly on totalCells (n²).</li>
 *   <li>If a dice roll would move a player beyond totalCells, the player does not move.</li>
 *   <li>The game ends when only 1 (or 0) active player(s) remain.</li>
 * </ul>
 */
public class Game {

    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private final Queue<Player> activePlayers;
    private final List<Player> winners;

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
        boolean isHard = dice.getDifficulty() == Dice.Difficulty.HARD;

        System.out.println("\n🎮 Game Started! Difficulty: " + dice.getDifficulty());
        if (isHard) {
            System.out.println("⚠️  HARD MODE: Rolling 3 consecutive sixes invalidates your turn!");
        } else {
            System.out.println("✅  EASY MODE: Consecutive sixes are always valid — keep rolling!");
        }
        System.out.println("Players: " + players.stream()
            .map(Player::getName).reduce((a, b) -> a + ", " + b).orElse(""));
        board.printLayout();

        int turnNumber = 0;

        while (activePlayers.size() > 1) {
            turnNumber++;
            Player current = activePlayers.poll();

            System.out.println("── Turn " + turnNumber + " ─ " + current.getName()
                + " (at position " + current.getPosition() + ") ──");

            // Remember position at turn start (needed for HARD mode invalidation)
            int positionAtTurnStart = current.getPosition();
            int consecutiveSixes = 0;
            boolean turnInvalidated = false;
            boolean won = false;

            // Keep rolling while the player gets a 6 (bonus roll rule)
            while (true) {
                int diceValue = dice.roll();
                System.out.println("  🎲 Rolled: " + diceValue);

                if (diceValue == 6) {
                    consecutiveSixes++;

                    // HARD MODE: 3 consecutive sixes → invalidate entire turn
                    if (isHard && consecutiveSixes == 3) {
                        System.out.println("  🚫 3 consecutive sixes in HARD mode! Turn INVALIDATED.");
                        System.out.println("     " + current.getName()
                            + " returns to position " + positionAtTurnStart + ".");
                        current.setPosition(positionAtTurnStart);
                        turnInvalidated = true;
                        break;
                    }

                    // Apply the move for this 6
                    int newPosition = current.getPosition() + diceValue;
                    if (newPosition > board.getTotalCells()) {
                        System.out.println("  ✋ Cannot move! " + newPosition
                            + " > " + board.getTotalCells()
                            + ". " + current.getName() + " stays at " + current.getPosition());
                    } else {
                        int finalPosition = board.getFinalPosition(newPosition);
                        current.setPosition(finalPosition);
                        System.out.println("  ➡️  " + current.getName()
                            + " moved to position " + finalPosition);

                        if (finalPosition == board.getTotalCells()) {
                            won = true;
                            break;
                        }
                    }

                    // Grant bonus roll
                    if (isHard) {
                        System.out.println("  🌟 Rolled a 6! ("
                            + consecutiveSixes + "/2 consecutive) — bonus roll!");
                    } else {
                        System.out.println("  🌟 Rolled a 6! Bonus roll!");
                    }

                } else {
                    // Normal roll (1–5): apply move, end turn
                    consecutiveSixes = 0;
                    int newPosition = current.getPosition() + diceValue;
                    if (newPosition > board.getTotalCells()) {
                        System.out.println("  ✋ Cannot move! " + newPosition
                            + " > " + board.getTotalCells()
                            + ". " + current.getName() + " stays at " + current.getPosition());
                    } else {
                        int finalPosition = board.getFinalPosition(newPosition);
                        current.setPosition(finalPosition);
                        System.out.println("  ➡️  " + current.getName()
                            + " moved to position " + finalPosition);

                        if (finalPosition == board.getTotalCells()) {
                            won = true;
                        }
                    }
                    break; // Turn ends after a non-six roll
                }
            }

            if (won) {
                winners.add(current);
                System.out.println("\n🏆 " + current.getName()
                    + " has WON! (Rank #" + winners.size() + ")\n");
                // Player is NOT added back to the queue → they've won
            } else {
                activePlayers.offer(current);
            }
        }

        // Game over
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("          🎉 GAME OVER 🎉");
        System.out.println("═══════════════════════════════════════");

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
