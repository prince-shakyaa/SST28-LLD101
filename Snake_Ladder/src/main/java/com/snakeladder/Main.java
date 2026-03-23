package com.snakeladder;

import com.snakeladder.model.Board;
import com.snakeladder.model.Player;
import com.snakeladder.service.Dice;
import com.snakeladder.service.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Snakes and Ladders game.
 *
 * <p>Reads three inputs from the user:
 * <ol>
 *   <li>n — board dimension (board is n×n containing n² cells)</li>
 *   <li>x — number of players (≥ 2)</li>
 *   <li>difficulty_level — "easy" or "hard"</li>
 * </ol>
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       🐍 SNAKES AND LADDERS 🪜        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();

        // --- Read n ---
        int n = 0;
        while (n < 2) {
            System.out.print("Enter board size n (board will be n×n, e.g. 10 → 10×10): ");
            try {
                n = Integer.parseInt(scanner.nextLine().trim());
                if (n < 2) {
                    System.out.println("⚠️  n must be at least 2. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid integer.");
            }
        }

        // --- Read x ---
        int x = 0;
        while (x < 2) {
            System.out.print("Enter number of players (minimum 2): ");
            try {
                x = Integer.parseInt(scanner.nextLine().trim());
                if (x < 2) {
                    System.out.println("⚠️  Need at least 2 players. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Please enter a valid integer.");
            }
        }

        // --- Read player names ---
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= x; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = "Player" + i;
            players.add(new Player(name));
        }

        // --- Read difficulty ---
        Dice.Difficulty difficulty = null;
        while (difficulty == null) {
            System.out.print("Enter difficulty level (easy / hard): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("easy")) {
                difficulty = Dice.Difficulty.EASY;
            } else if (input.equals("hard")) {
                difficulty = Dice.Difficulty.HARD;
            } else {
                System.out.println("⚠️  Please enter 'easy' or 'hard'.");
            }
        }

        scanner.close();

        // --- Setup & Start ---
        Board board = new Board(n);
        Dice dice = new Dice(difficulty);
        Game game = new Game(board, dice, players);
        game.start();
    }
}
