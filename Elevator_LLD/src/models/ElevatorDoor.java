package models;

import enums.DoorState;
import enums.ElevatorState;

public class ElevatorDoor {
    private DoorState doorState;
    private int elevatorId;

    public ElevatorDoor(int elevatorId) {
        this.elevatorId = elevatorId;
        this.doorState = DoorState.CLOSED;
    }

    public boolean open(ElevatorState elevatorState) {
        if (elevatorState == ElevatorState.IDLE) {
            this.doorState = DoorState.OPEN;
            System.out.println("[Elevator " + elevatorId + "] Door OPENED.");
            return true;
        }
        System.out.println("[Elevator " + elevatorId + "] Cannot open door while elevator is moving.");
        return false;
    }

    public void close() {
        this.doorState = DoorState.CLOSED;
        System.out.println("[Elevator " + elevatorId + "] Door CLOSED.");
    }

    public DoorState getDoorState() {
        return doorState;
    }
}
