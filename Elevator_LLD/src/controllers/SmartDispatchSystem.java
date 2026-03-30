package controllers;

import enums.Direction;
import enums.ElevatorState;
import models.ElevatorCar;
import models.Request;

import java.util.List;

public class SmartDispatchSystem {
    private List<ElevatorCar> elevatorCars;
    private List<ElevatorController> elevatorControllers;

    public SmartDispatchSystem(List<ElevatorCar> elevatorCars, List<ElevatorController> elevatorControllers) {
        this.elevatorCars = elevatorCars;
        this.elevatorControllers = elevatorControllers;
    }

    public ElevatorController dispatch(Request request) {
        ElevatorController bestController = null;
        int bestScore = Integer.MAX_VALUE;

        for (int i = 0; i < elevatorCars.size(); i++) {
            ElevatorCar car = elevatorCars.get(i);
            if (car.isFull()) {
                continue;
            }
            int score = calculateScore(car, request);
            if (score < bestScore) {
                bestScore = score;
                bestController = elevatorControllers.get(i);
            }
        }

        if (bestController != null) {
            request.setAssignedElevatorId(bestController.getElevatorCar().getId());
            int sourceFloor = request.getSourceFloor();
            int destinationFloor = request.getDestinationFloor();

            System.out.println("[Dispatch] Elevator " + bestController.getElevatorCar().getId()
                    + " assigned to request from floor " + sourceFloor + " to floor " + destinationFloor);

            bestController.addDestinationFloor(sourceFloor);
            bestController.addDestinationFloor(destinationFloor);
        } else {
            System.out.println("[Dispatch] No available elevator found for request from floor "
                    + request.getSourceFloor());
        }

        return bestController;
    }

    private int calculateScore(ElevatorCar car, Request request) {
        int floorDifference = Math.abs(car.getCurrentFloor() - request.getSourceFloor());

        if (car.getState() == ElevatorState.IDLE) {
            return floorDifference;
        }

        if (car.getDirection() == request.getDirection()) {
            if (car.getDirection() == Direction.UP && car.getCurrentFloor() <= request.getSourceFloor()) {
                return floorDifference;
            }
            if (car.getDirection() == Direction.DOWN && car.getCurrentFloor() >= request.getSourceFloor()) {
                return floorDifference;
            }
        }

        return floorDifference + 20;
    }

    public void dispatchAll(List<Request> requests) {
        for (Request request : requests) {
            dispatch(request);
        }
        for (ElevatorController controller : elevatorControllers) {
            if (!controller.getElevatorCar().getUpQueue().isEmpty()
                    || !controller.getElevatorCar().getDownQueue().isEmpty()) {
                controller.processRequests();
            }
        }
    }
}
