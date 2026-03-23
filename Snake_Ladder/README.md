# Snakes & Ladders – LLD Assignment

A fully object-oriented **Snakes & Ladders** game implemented in Java.

## Features

| Feature | Detail |
|---|---|
| Configurable board | n × n grid (positions 1 – n²) |
| Configurable players | Any number ≥ 2 |
| Random placement | n snakes **and** n ladders, no overlaps, no cycles |
| Difficulty levels | **Easy** / **Hard** (see below) |
| Overshot rule | Piece does NOT move if it would exceed n² |
| Win condition | Player reaches position n² |
| Game-end condition | Game stops when only 1 player remains active |

## Difficulty Differentiation (Consecutive Sixes Rule)

| Level | Rule |
|---|---|
| **EASY** | **Unlimited** consecutive sixes — all turns valid, no cancellation. |
| **HARD** | ≤ 2 consecutive sixes → valid move. **3rd** consecutive six → turn **cancelled**, goes to next player. |

## Project Structure

```
Snake&Ladder/
├── src/
│   ├── DifficultyLevel.java   # Enum: EASY / HARD
│   ├── Snake.java             # head > tail
│   ├── Ladder.java            # start < end
│   ├── Cell.java              # (reserved for future extension)
│   ├── Player.java            # position, consecutive-sixes tracker
│   ├── Dice.java              # 6-sided random die
│   ├── Board.java             # random placement + cell resolution
│   ├── Game.java              # turn loop + difficulty rules
│   └── Main.java              # entry point (Scanner input)
├── out/                       # compiled .class files
├── class_diagram.md           # UML class diagram (PlantUML)
└── README.md
```

## How to Compile & Run

```bash
# Compile
javac -d out src/*.java

# Run
java -cp out Main
```

### Sample Interaction

```
Enter board size n (creates n×n board, min 2): 10
Enter number of players (min 2): 3
Enter difficulty level (easy/hard): easy
Enter name for Player 1: Alice
Enter name for Player 2: Bob
Enter name for Player 3: Charlie
```

## Class Diagram

See [`class_diagram.md`](class_diagram.md) for the full PlantUML diagram.

## Rules

1. Board has positions **1 to n²**.
2. Players take turns rolling a 6-sided die.
3. Piece starts at position **0** (outside the board).
4. Landing on a **snake's head** → slide to its tail.
5. Landing on a **ladder's start** → climb to its end.
6. If a roll would push a piece **past n²**, the piece does **not** move.
7. A player **wins** by reaching exactly **n²**.
8. Game ends when only **1** player has not yet won.
9. Consecutive sixes forfeit rule applies based on difficulty (see above).
