package models;

import enums.Direction;
import enums.DoorState;
import enums.ElevatorState;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ElevatorCar {
    private int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private ElevatorDoor door;
    private List<Passenger> passengers;
    private double currentWeight;
    private static final int MAX_CAPACITY_PEOPLE = 8;
    private static final double MAX_CAPACITY_WEIGHT = 680.0;
    private InternalDisplay internalDisplay;
    private ExternalDisplay externalDisplay;
    private List<InternalButton> internalButtons;
    private TreeSet<Integer> upQueue;
    private TreeSet<Integer> downQueue;
    private int totalFloors;

    public ElevatorCar(int id, int totalFloors) {
        this.id = id;
        this.totalFloors = totalFloors;
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.door = new ElevatorDoor(id);
        this.passengers = new ArrayList<>();
        this.currentWeight = 0.0;
        this.internalDisplay = new InternalDisplay(id, MAX_CAPACITY_PEOPLE, MAX_CAPACITY_WEIGHT);
        this.externalDisplay = new ExternalDisplay(id);
        this.internalButtons = new ArrayList<>();
        this.upQueue = new TreeSet<>();
        this.downQueue = new TreeSet<>((a, b) -> b - a);

        for (int i = 1; i <= totalFloors; i++) {
            internalButtons.add(new InternalButton(i));
        }
    }

    public boolean addPassenger(Passenger passenger) {
        if (passengers.size() >= MAX_CAPACITY_PEOPLE) {
            System.out.println("[Elevator " + id + "] At maximum passenger capacity. Cannot board.");
            return false;
        }
        if (currentWeight + passenger.getWeight() > MAX_CAPACITY_WEIGHT) {
            System.out.println("[Elevator " + id + "] Weight limit exceeded. Cannot board passenger.");
            return false;
        }
        passengers.add(passenger);
        currentWeight += passenger.getWeight();
        internalDisplay.updateLoad(passengers.size());
        System.out.println("[Elevator " + id + "] Passenger " + passenger.getId() + " boarded. Load: "
                + passengers.size() + "/" + MAX_CAPACITY_PEOPLE);
        return true;
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        currentWeight -= passenger.getWeight();
        internalDisplay.updateLoad(passengers.size());
        System.out.println("[Elevator " + id + "] Passenger " + passenger.getId() + " deboarded at floor " + currentFloor);
    }

    public void addFloorToQueue(int floor) {
        if (floor > currentFloor) {
            upQueue.add(floor);
        } else if (floor < currentFloor) {
            downQueue.add(floor);
        }
    }

    public void pressInternalButton(int floorNumber) {
        if (floorNumber >= 1 && floorNumber <= totalFloors) {
            internalButtons.get(floorNumber - 1).press();
            addFloorToQueue(floorNumber);
        }
    }

    public void openDoor() {
        door.open(this.state);
    }

    public void closeDoor() {
        door.close();
    }

    public void moveUp() {
        if (door.getDoorState() == DoorState.OPEN) {
            System.out.println("[Elevator " + id + "] Cannot move. Door is open.");
            return;
        }
        if (currentFloor < totalFloors) {
            this.state = ElevatorState.MOVING;
            this.direction = Direction.UP;
            currentFloor++;
            updateDisplays();
        }
    }

    public void moveDown() {
        if (door.getDoorState() == DoorState.OPEN) {
            System.out.println("[Elevator " + id + "] Cannot move. Door is open.");
            return;
        }
        if (currentFloor > 1) {
            this.state = ElevatorState.MOVING;
            this.direction = Direction.DOWN;
            currentFloor--;
            updateDisplays();
        }
    }

    public void stop() {
        this.state = ElevatorState.IDLE;
        this.direction = Direction.IDLE;
        System.out.println("[Elevator " + id + "] Stopped at floor " + currentFloor);
        updateDisplays();
    }

    private void updateDisplays() {
        internalDisplay.update(currentFloor, direction);
        externalDisplay.update(currentFloor, direction);
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public ElevatorDoor getDoor() {
        return door;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public InternalDisplay getInternalDisplay() {
        return internalDisplay;
    }

    public ExternalDisplay getExternalDisplay() {
        return externalDisplay;
    }

    public TreeSet<Integer> getUpQueue() {
        return upQueue;
    }

    public TreeSet<Integer> getDownQueue() {
        return downQueue;
    }

    public int getMaxCapacityPeople() {
        return MAX_CAPACITY_PEOPLE;
    }

    public double getMaxCapacityWeight() {
        return MAX_CAPACITY_WEIGHT;
    }

    public boolean isFull() {
        return passengers.size() >= MAX_CAPACITY_PEOPLE || currentWeight >= MAX_CAPACITY_WEIGHT;
    }
}
