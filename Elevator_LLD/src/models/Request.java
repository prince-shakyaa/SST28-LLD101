package models;

import enums.Direction;

public class Request {
    private int sourceFloor;
    private int destinationFloor;
    private Direction direction;
    private int assignedElevatorId;

    public Request(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = (destinationFloor > sourceFloor) ? Direction.UP : Direction.DOWN;
        this.assignedElevatorId = -1;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getAssignedElevatorId() {
        return assignedElevatorId;
    }

    public void setAssignedElevatorId(int elevatorId) {
        this.assignedElevatorId = elevatorId;
    }
}
