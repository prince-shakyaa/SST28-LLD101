package models;

import enums.Direction;

public class InternalDisplay extends Display {
    private int currentLoad;
    private int maxCapacityPeople;
    private double maxCapacityWeight;
    private int elevatorId;

    public InternalDisplay(int elevatorId, int maxCapacityPeople, double maxCapacityWeight) {
        super();
        this.elevatorId = elevatorId;
        this.maxCapacityPeople = maxCapacityPeople;
        this.maxCapacityWeight = maxCapacityWeight;
        this.currentLoad = 0;
    }

    @Override
    public void showDisplay() {
        System.out.println("[Elevator " + elevatorId + " Internal Display] Floor: " + currentFloor
                + " | Direction: " + direction
                + " | Load: " + currentLoad + "/" + maxCapacityPeople + " people");
    }

    public void updateLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }
}
