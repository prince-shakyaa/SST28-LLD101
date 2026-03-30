package controllers;

import enums.Direction;
import enums.DoorState;
import enums.ElevatorState;
import models.ElevatorCar;
import models.Passenger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElevatorController {
    private ElevatorCar elevatorCar;

    public ElevatorController(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
    }

    public void addDestinationFloor(int floor) {
        elevatorCar.addFloorToQueue(floor);
    }

    public void processRequests() {
        while (!elevatorCar.getUpQueue().isEmpty() || !elevatorCar.getDownQueue().isEmpty()) {
            if (elevatorCar.getDirection() == Direction.UP || elevatorCar.getDirection() == Direction.IDLE) {
                processUpRequests();
            }
            if (elevatorCar.getDirection() == Direction.DOWN || elevatorCar.getDirection() == Direction.IDLE) {
                processDownRequests();
            }
        }
        elevatorCar.stop();
    }

    private void processUpRequests() {
        while (!elevatorCar.getUpQueue().isEmpty()) {
            int targetFloor = elevatorCar.getUpQueue().first();
            elevatorCar.getUpQueue().remove(targetFloor);
            moveToFloor(targetFloor);
            handleArrival();
        }
    }

    private void processDownRequests() {
        while (!elevatorCar.getDownQueue().isEmpty()) {
            int targetFloor = elevatorCar.getDownQueue().first();
            elevatorCar.getDownQueue().remove(targetFloor);
            moveToFloor(targetFloor);
            handleArrival();
        }
    }

    private void moveToFloor(int targetFloor) {
        System.out.println("[Elevator " + elevatorCar.getId() + "] Moving from floor "
                + elevatorCar.getCurrentFloor() + " to floor " + targetFloor);
        while (elevatorCar.getCurrentFloor() != targetFloor) {
            if (elevatorCar.getCurrentFloor() < targetFloor) {
                elevatorCar.moveUp();
            } else {
                elevatorCar.moveDown();
            }
            elevatorCar.getInternalDisplay().showDisplay();
            elevatorCar.getExternalDisplay().showDisplay();
        }
    }

    private void handleArrival() {
        elevatorCar.stop();
        elevatorCar.openDoor();

        List<Passenger> toRemove = new ArrayList<>();
        for (Passenger p : elevatorCar.getPassengers()) {
            if (p.getDestinationFloor() == elevatorCar.getCurrentFloor()) {
                toRemove.add(p);
            }
        }
        for (Passenger p : toRemove) {
            elevatorCar.removePassenger(p);
        }

        elevatorCar.closeDoor();
    }

    public void boardPassenger(Passenger passenger) {
        if (elevatorCar.getState() == ElevatorState.IDLE
                && elevatorCar.getDoor().getDoorState() == DoorState.OPEN) {
            boolean boarded = elevatorCar.addPassenger(passenger);
            if (boarded) {
                elevatorCar.pressInternalButton(passenger.getDestinationFloor());
            }
        } else {
            System.out.println("[Elevator " + elevatorCar.getId() + "] Door is not open. Cannot board passenger "
                    + passenger.getId());
        }
    }

    public ElevatorCar getElevatorCar() {
        return elevatorCar;
    }
}
