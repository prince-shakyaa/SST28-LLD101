package models;

import enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int floorNumber;
    private ExternalButton upButton;
    private ExternalButton downButton;
    private List<ExternalDisplay> elevatorDisplays;

    public Floor(int floorNumber, int totalElevators) {
        this.floorNumber = floorNumber;
        this.upButton = new ExternalButton(floorNumber, Direction.UP);
        this.downButton = new ExternalButton(floorNumber, Direction.DOWN);
        this.elevatorDisplays = new ArrayList<>();
        for (int i = 1; i <= totalElevators; i++) {
            elevatorDisplays.add(new ExternalDisplay(i));
        }
    }

    public void pressUpButton() {
        upButton.press();
    }

    public void pressDownButton() {
        downButton.press();
    }

    public void showAllDisplays() {
        System.out.println("--- Floor " + floorNumber + " Panel ---");
        for (ExternalDisplay display : elevatorDisplays) {
            display.showDisplay();
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ExternalButton getUpButton() {
        return upButton;
    }

    public ExternalButton getDownButton() {
        return downButton;
    }

    public List<ExternalDisplay> getElevatorDisplays() {
        return elevatorDisplays;
    }

    public ExternalDisplay getDisplayForElevator(int elevatorId) {
        for (ExternalDisplay display : elevatorDisplays) {
            return display;
        }
        return null;
    }
}
