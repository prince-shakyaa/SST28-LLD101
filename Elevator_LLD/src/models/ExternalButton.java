package models;

import enums.Direction;

public class ExternalButton extends Button {
    private int floorNumber;
    private Direction direction;

    public ExternalButton(int floorNumber, Direction direction) {
        super();
        this.floorNumber = floorNumber;
        this.direction = direction;
    }

    @Override
    public void press() {
        this.isPressed = true;
        System.out.println("External button pressed on floor " + floorNumber + " - Direction: " + direction);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Direction getDirection() {
        return direction;
    }
}
