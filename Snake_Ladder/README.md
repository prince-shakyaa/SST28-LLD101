# 🐍 Snakes and Ladders — LLD Implementation

> **LLD 101 Assignment** — Low Level Design implementation of the classic Snakes and Ladders board game in Java.

---

## 📐 Class Diagram

![Class Diagram](class_diagram.puml)

The PlantUML source is in [`class_diagram.puml`](class_diagram.puml). Render it at [plantuml.com](https://www.plantuml.com/plantuml/uml/) or using the PlantUML VS Code extension.

### Design Overview

```
com.snakeladder
├── model/
│   ├── Cell.java      — One board cell; optionally holds a Snake head or Ladder start
│   ├── Snake.java     — Snake with head (high) and tail (low)
│   ├── Ladder.java    — Ladder with start (low) and end (high)
│   ├── Player.java    — Player name + position (0 = off-board)
│   └── Board.java     — n×n board; randomly places n snakes + n ladders
├── service/
│   ├── Dice.java      — 6-sided (EASY) or 12-sided (HARD) dice
│   └── Game.java      — Turn-by-turn game loop, win detection
└── Main.java          — CLI entry point (reads n, players, difficulty)
```

---

## 🎮 Game Rules

| Rule | Implementation |
|---|---|
| Board size | n×n cells, numbered 1 to n² |
| Players | Each starts at position 0 (off-board) |
| Turn order | Round-robin, turn by turn |
| Dice | EASY: 1–6 · HARD: 1–12 |
| Movement | `new_pos = current + dice`; if `new_pos > n²` → **do not move** |
| Snake | Land on head → slide down to tail |
| Ladder | Land on start → climb up to end |
| Win | Reach exactly n² |
| End condition | Game stops when ≤1 player is still active |
| No cycles | Snakes/ladders are placed so no destination is itself a snake head or ladder start |

---

## 🚀 Running the Application

### Prerequisites
- Java 11+
- Maven 3.6+

### Build & Run

```bash
# Clone the repository
git clone https://github.com/prince-shakyaa/Snake_Ladder.git
cd Snake_Ladder

# Compile
mvn compile

# Run (interactive CLI)
mvn exec:java -Dexec.mainClass=com.snakeladder.Main
```

### Sample Input / Output

```
╔══════════════════════════════════════╗
║       🐍 SNAKES AND LADDERS 🪜        ║
╚══════════════════════════════════════╝

Enter board size n (board will be n×n, e.g. 10 → 10×10): 4
Enter number of players (minimum 2): 2
Enter name for Player 1: Alice
Enter name for Player 2: Bob
Enter difficulty level (easy / hard): easy

🎮 Game Started! Difficulty: EASY
Players: Alice, Bob

📋 Board Layout (4×4 = 16 cells)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🐍 Snakes (4):
   Head: 15  →  Tail: 3
   ...
🪜  Ladders (4):
   Start: 4  →  End: 14
   ...
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

── Turn 1 ─ Alice (at position 0) ──
  🎲 Rolled: 4
  🪜  Ladder at 4 → climbs up to 14
  ➡️  Alice moved to position 14

── Turn 2 ─ Bob (at position 0) ──
  🎲 Rolled: 6
  ➡️  Bob moved to position 6
...
```

---

## 🏗️ Design Decisions

1. **`Board` owns placement logic** — Random snake/ladder placement with no-cycle and no-overlap guarantees is encapsulated entirely in `Board.placeSnakesAndLadders()`.

2. **`Queue<Player>` for turn management** — `Game` uses a `LinkedList` as a queue; winning players are simply not re-added.

3. **Difficulty via `Dice`** — Swapping difficulty only changes the dice range; all other game logic stays the same (Open/Closed Principle).

4. **No cycle guarantee** — The board tracks occupied *starts* and occupied *destinations* separately; a destination can never itself be a start.

5. **1-indexed `Cell[]`** — `cells[0]` is unused to keep indexing intuitive (`cells[pos]` for position `pos`).

---

## 📁 Project Structure

```
Snake_Ladder/
├── pom.xml
├── README.md
├── class_diagram.puml
└── src/
    └── main/
        └── java/
            └── com/snakeladder/
                ├── Main.java
                ├── model/
                │   ├── Cell.java
                │   ├── Snake.java
                │   ├── Ladder.java
                │   ├── Player.java
                │   └── Board.java
                └── service/
                    ├── Dice.java
                    └── Game.java
```

---

## 👤 Author

**Shakya Prince** — [github.com/prince-shakyaa](https://github.com/prince-shakyaa)
