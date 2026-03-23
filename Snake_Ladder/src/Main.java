import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     SNAKES & LADDERS GAME        ║");
        System.out.println("╚══════════════════════════════════╝\n");

        int n = 0;
        while (n < 2) {
            System.out.print("Enter board size n (creates n×n board, min 2): ");
            try {
                n = Integer.parseInt(sc.nextLine().trim());
                if (n < 2) System.out.println("  Board size must be at least 2.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter an integer.");
            }
        }

        int x = 0;
        while (x < 2) {
            System.out.print("Enter number of players (min 2): ");
            try {
                x = Integer.parseInt(sc.nextLine().trim());
                if (x < 2) System.out.println("  Need at least 2 players.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter an integer.");
            }
        }

        DifficultyLevel difficulty = null;
        while (difficulty == null) {
            System.out.print("Enter difficulty level (easy/hard): ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("easy")) {
                difficulty = DifficultyLevel.EASY;
            } else if (input.equals("hard")) {
                difficulty = DifficultyLevel.HARD;
            } else {
                System.out.println("  Please enter 'easy' or 'hard'.");
            }
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= x; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) name = "Player" + i;
            players.add(new Player(name));
        }

        Board board = new Board(n);
        Game game   = new Game(board, players, difficulty);
        game.start();

        sc.close();
    }
}
