package models;

import enums.Direction;

public class ExternalDisplay extends Display {
    private int elevatorId;

    public ExternalDisplay(int elevatorId) {
        super();
        this.elevatorId = elevatorId;
    }

    @Override
    public void showDisplay() {
        System.out.println("[Elevator " + elevatorId + " External Display] Floor: " + currentFloor
                + " | Direction: " + direction);
    }
}
