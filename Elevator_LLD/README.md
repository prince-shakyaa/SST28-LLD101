# Elevator System — Low-Level Design (LLD)

## Overview

A fully object-oriented Low-Level Design of an Elevator Control System implemented in Java.

## Requirements Covered

| Requirement | Detail |
|---|---|
| Elevators | 3 elevator cars |
| Floors | Up to 15 floors |
| Capacity | 8 people / 680 kg per elevator |
| Movement | UP, DOWN, IDLE |
| Door Operation | Opens only when elevator is IDLE |
| Outside Panel | UP/DOWN external buttons on every floor |
| Inside Panel | Floor buttons + door open/close per elevator |
| External Display | Current floor + direction (per floor, per elevator) |
| Internal Display | Current floor + direction + passenger load |
| Smart Dispatch | Assigns most optimal elevator based on proximity & trajectory |
| Multi-passenger | Multiple passengers to different floors handled simultaneously |

## Project Structure

```
Elevator_LLD/
└── src/
    ├── Main.java
    ├── enums/
    │   ├── Direction.java
    │   ├── DoorState.java
    │   └── ElevatorState.java
    ├── models/
    │   ├── Button.java
    │   ├── InternalButton.java
    │   ├── ExternalButton.java
    │   ├── Display.java
    │   ├── InternalDisplay.java
    │   ├── ExternalDisplay.java
    │   ├── ElevatorDoor.java
    │   ├── ElevatorCar.java
    │   ├── Floor.java
    │   ├── Passenger.java
    │   ├── Request.java
    │   └── Building.java
    └── controllers/
        ├── ElevatorController.java
        └── SmartDispatchSystem.java
```

## Design Patterns Used

- **Singleton** — `Building` class ensures one building instance system-wide
- **Strategy (Scoring)** — `SmartDispatchSystem` scores each elevator and picks the best
- **Abstraction** — `Button` and `Display` are abstract base classes extended by internal/external variants

## Class Descriptions

| Class | Role |
|---|---|
| `Direction` | Enum: UP, DOWN, IDLE |
| `ElevatorState` | Enum: MOVING, IDLE, MAINTENANCE |
| `DoorState` | Enum: OPEN, CLOSED |
| `Passenger` | Holds passenger id, weight, source/destination floor |
| `Request` | Represents a floor call with source, destination, direction |
| `Button` | Abstract base for elevator buttons |
| `InternalButton` | Button inside elevator mapped to a floor number |
| `ExternalButton` | Button on a floor with a direction (UP/DOWN) |
| `Display` | Abstract base for elevator displays |
| `InternalDisplay` | Shows floor, direction, load inside elevator |
| `ExternalDisplay` | Shows floor and direction outside elevator |
| `ElevatorDoor` | Manages door open/close with state enforcement |
| `ElevatorCar` | Represents one elevator car with all components |
| `Floor` | Holds external buttons and displays for each floor |
| `Building` | Singleton: owns all floors, elevators, and the dispatcher |
| `ElevatorController` | Controls a single elevator's movement and stops |
| `SmartDispatchSystem` | Assigns the optimal elevator to every incoming request |

## How to Compile and Run

```bash
# From the Elevator_LLD directory
mkdir -p out
javac -d out -sourcepath src $(find src -name "*.java")
java -cp out Main
```

## Smart Dispatch Algorithm

For each incoming `Request`, the system scores every elevator:

- **IDLE elevator**: score = `|currentFloor - requestSourceFloor|`
- **Moving elevator, same direction, en-route**: score = `|currentFloor - requestSourceFloor|`
- **Moving elevator, wrong direction**: score = `|currentFloor - requestSourceFloor| + 20` (penalty)

The elevator with the **lowest score** is assigned.
