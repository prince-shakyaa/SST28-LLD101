import java.util.*;

public class Board {
    private final int n;
    private final int totalCells;
    private final Map<Integer, Snake> snakeMap;
    private final Map<Integer, Ladder> ladderMap;

    public Board(int n) {
        this.n = n;
        this.totalCells = n * n;
        this.snakeMap = new HashMap<>();
        this.ladderMap = new HashMap<>();
        placeSnakesAndLadders();
    }

    private void placeSnakesAndLadders() {
        Set<Integer> occupied = new HashSet<>();

        occupied.add(1);
        occupied.add(totalCells);

        System.out.println("\n-- Board Setup (" + n + "x" + n + ") --");

        int snakesPlaced = 0;
        while (snakesPlaced < n) {
            int head = randomCell(2, totalCells - 1, occupied);
            if (head == -1) {
                System.out.println("Warning: Not enough cells for more snakes.");
                break;
            }
            int tail = randomCell(1, head - 1, occupied);
            if (tail == -1)
                continue;

            snakeMap.put(head, new Snake(head, tail));
            occupied.add(head);
            occupied.add(tail);
            snakesPlaced++;
            System.out.println("  Placed " + snakeMap.get(head));
        }

        // Place n ladders
        int laddersPlaced = 0;
        while (laddersPlaced < n) {
            int start = randomCell(2, totalCells - 1, occupied);
            if (start == -1) {
                System.out.println("Warning: Not enough cells for more ladders.");
                break;
            }
            int end = randomCell(start + 1, totalCells - 1, occupied);
            if (end == -1)
                continue;

            ladderMap.put(start, new Ladder(start, end));
            occupied.add(start);
            occupied.add(end);
            laddersPlaced++;
            System.out.println("  Placed " + ladderMap.get(start));
        }
    }

    private int randomCell(int low, int high, Set<Integer> occupied) {
        if (low > high)
            return -1;
        List<Integer> candidates = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (!occupied.contains(i))
                candidates.add(i);
        }
        if (candidates.isEmpty())
            return -1;
        Collections.shuffle(candidates);
        return candidates.get(0);
    }

    public int applyCell(int position) {
        if (snakeMap.containsKey(position)) {
            int tail = snakeMap.get(position).getTail();
            System.out.println("  🐍 Oops! Snake at " + position + "! Sliding down to " + tail);
            return tail;
        }
        if (ladderMap.containsKey(position)) {
            int end = ladderMap.get(position).getEnd();
            System.out.println("  🪜 Lucky! Ladder at " + position + "! Climbing up to " + end);
            return end;
        }
        return position;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public int getN() {
        return n;
    }

    public Map<Integer, Snake> getSnakeMap() {
        return Collections.unmodifiableMap(snakeMap);
    }

    public Map<Integer, Ladder> getLadderMap() {
        return Collections.unmodifiableMap(ladderMap);
    }
}
