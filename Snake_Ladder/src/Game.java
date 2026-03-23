import java.util.*;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private final DifficultyLevel difficulty;

    // EASY → unlimited consecutive sixes (Integer.MAX_VALUE = never cancel)
    // HARD → max 2 consecutive sixes; 3rd cancels the turn
    private final int maxConsecutiveSixes;

    public Game(Board board, List<Player> players, DifficultyLevel difficulty) {
        this.board      = board;
        this.players    = new ArrayList<>(players);
        this.dice       = new Dice();
        this.difficulty = difficulty;
        this.maxConsecutiveSixes = (difficulty == DifficultyLevel.EASY) ? Integer.MAX_VALUE : 2;
    }

    public void start() {
        System.out.println("\n========================================");
        System.out.println("  SNAKES & LADDERS  [" + difficulty + " MODE]");
        System.out.println("  Board: " + board.getN() + "x" + board.getN()
                + "  |  Goal: " + board.getTotalCells());
        String sixRule = (difficulty == DifficultyLevel.EASY)
                ? "Unlimited consecutive sixes (EASY)"
                : "Max 2 consecutive sixes – 3rd cancels turn (HARD)";
        System.out.println("  Six rule: " + sixRule);
        System.out.println("========================================\n");

        int rank = 1; 
        int turnIndex = 0;

        while (activePlayers() >= 2) {
            List<Player> active = getActivePlayers();
            Player current = active.get(turnIndex % active.size());
            turnIndex++;

            System.out.println("-- " + current.getName() + "'s turn (pos=" + current.getPosition() + ") --");

            int diceValue = dice.roll();
            System.out.println("  Rolled: " + diceValue);

            if (diceValue == 6) {
                current.incrementConsecutiveSixes();
                System.out.println("  Consecutive sixes so far: " + current.getConsecutiveSixes());

                if (current.getConsecutiveSixes() > maxConsecutiveSixes) {
                    // Turn cancelled – move to next player
                    System.out.println("  ❌ " + current.getName()
                            + " got " + current.getConsecutiveSixes()
                            + " consecutive sixes! Turn cancelled. Position stays at "
                            + current.getPosition() + ".");
                    current.resetConsecutiveSixes();
                    System.out.println();
                    continue;
                }
            } else {
                // Non-six resets the streak
                current.resetConsecutiveSixes();
            }

            int newPos = current.getPosition() + diceValue;

            if (newPos > board.getTotalCells()) {
                System.out.println("  ⛔ Overshot! " + current.getName()
                        + " cannot move (would go to " + newPos
                        + " > " + board.getTotalCells() + ").");
                System.out.println();
                continue;
            }

            newPos = board.applyCell(newPos);
            current.setPosition(newPos);
            System.out.println("  ✅ " + current.getName() + " moves to position " + newPos);

            // Check win condition
            if (newPos == board.getTotalCells()) {
                current.setHasWon(true);
                System.out.println("  🏆 " + current.getName() + " has WON! (Rank #" + rank + ")");
                rank++;
            }

            System.out.println();
        }

        announceResult(rank);
    }

    private List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>();
        for (Player p : players) {
            if (!p.hasWon()) active.add(p);
        }
        return active;
    }

    private int activePlayers() {
        return getActivePlayers().size();
    }

    private void announceResult(int nextRank) {
        System.out.println("========================================");
        System.out.println("  GAME OVER");
        System.out.println("========================================");

        System.out.println("\nFinal Standings:");
        int rank = 1;
        for (Player p : players) {
            if (p.hasWon()) {
                System.out.println("  #" + rank + " " + p.getName());
                rank++;
            }
        }
        // Remaining (did not win / game ended with them still in)
        for (Player p : players) {
            if (!p.hasWon()) {
                System.out.println("  #" + rank + " " + p.getName() + " (did not finish)");
                rank++;
            }
        }
    }
}
