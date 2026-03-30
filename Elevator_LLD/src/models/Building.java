package models;

import controllers.ElevatorController;
import controllers.SmartDispatchSystem;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private static Building instance;
    private int totalFloors;
    private int totalElevators;
    private List<Floor> floors;
    private List<ElevatorCar> elevatorCars;
    private List<ElevatorController> elevatorControllers;
    private SmartDispatchSystem smartDispatchSystem;

    private Building(int totalFloors, int totalElevators) {
        this.totalFloors = totalFloors;
        this.totalElevators = totalElevators;
        this.floors = new ArrayList<>();
        this.elevatorCars = new ArrayList<>();
        this.elevatorControllers = new ArrayList<>();

        for (int i = 1; i <= totalFloors; i++) {
            floors.add(new Floor(i, totalElevators));
        }

        for (int i = 1; i <= totalElevators; i++) {
            ElevatorCar car = new ElevatorCar(i, totalFloors);
            elevatorCars.add(car);
            elevatorControllers.add(new ElevatorController(car));
        }

        this.smartDispatchSystem = new SmartDispatchSystem(elevatorCars, elevatorControllers);
    }

    public static Building getInstance(int totalFloors, int totalElevators) {
        if (instance == null) {
            instance = new Building(totalFloors, totalElevators);
        }
        return instance;
    }

    public static Building getInstance() {
        return instance;
    }

    public SmartDispatchSystem getSmartDispatchSystem() {
        return smartDispatchSystem;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public List<ElevatorCar> getElevatorCars() {
        return elevatorCars;
    }

    public List<ElevatorController> getElevatorControllers() {
        return elevatorControllers;
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber - 1);
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    public int getTotalElevators() {
        return totalElevators;
    }
}
