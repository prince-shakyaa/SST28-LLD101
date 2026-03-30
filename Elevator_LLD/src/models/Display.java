package models;

import enums.Direction;

public abstract class Display {
    protected int currentFloor;
    protected Direction direction;

    public Display() {
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
    }

    public abstract void showDisplay();

    public void update(int currentFloor, Direction direction) {
        this.currentFloor = currentFloor;
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}
