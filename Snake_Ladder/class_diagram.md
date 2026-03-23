# Snakes & Ladders – Class Diagram

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam monochrome false
skinparam shadowing false

enum DifficultyLevel {
  EASY
  HARD
}

class Snake {
  - head : int
  - tail : int
  + getHead() : int
  + getTail() : int
}

class Ladder {
  - start : int
  - end   : int
  + getStart() : int
  + getEnd()   : int
}

class Player {
  - name              : String
  - position          : int
  - consecutiveSixes  : int
  - hasWon            : boolean
  + getPosition()         : int
  + setPosition(int)      : void
  + incrementConsecutiveSixes() : void
  + resetConsecutiveSixes()     : void
  + hasWon()              : boolean
  + setHasWon(boolean)    : void
}

class Dice {
  - random : Random
  + roll() : int
}

class Board {
  - n          : int
  - totalCells : int
  - snakeMap   : Map<Integer, Snake>
  - ladderMap  : Map<Integer, Ladder>
  + applyCell(int) : int
  + getTotalCells() : int
  + getN()          : int
}

class Game {
  - board              : Board
  - players            : List<Player>
  - dice               : Dice
  - difficulty         : DifficultyLevel
  - maxConsecutiveSixes: int
  + start() : void
  - activePlayers()     : int
  - getActivePlayers()  : List<Player>
  - announceResult(int) : void
}

class Main {
  + main(String[]) : void
}

' Relationships
Board "1" o-- "n" Snake    : places
Board "1" o-- "n" Ladder   : places
Game  "1" *-- "1" Board    : has
Game  "1" *-- "1" Dice     : rolls
Game  "1" *-- "1..*" Player : manages
Game  "1" --> DifficultyLevel : uses
Main  ..>  Game             : creates
Main  ..>  Board            : creates
Main  ..>  Player           : creates

@enduml
```

## Relationship Summary

| Class | Role |
|---|---|
| `Main` | Entry point; reads user input, instantiates `Board`, `Player` list, and `Game` |
| `DifficultyLevel` | Enum controlling the consecutive-sixes threshold (EASY→2, HARD→1) |
| `Board` | Creates n×n grid; randomly places n `Snake`s and n `Ladder`s; resolves cell effects |
| `Snake` | Encapsulates head (higher) & tail (lower) positions |
| `Ladder` | Encapsulates start (lower) & end (higher) positions |
| `Dice` | Produces a random 1–6 roll |
| `Player` | Tracks position, consecutive-six streak, and win status |
| `Game` | Orchestrates the turn loop, enforces difficulty rules, detects wins |
