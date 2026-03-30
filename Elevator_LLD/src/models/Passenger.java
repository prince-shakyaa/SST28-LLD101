package models;

public class Passenger {
    private int id;
    private double weight;
    private int currentFloor;
    private int destinationFloor;

    public Passenger(int id, double weight, int currentFloor, int destinationFloor) {
        this.id = id;
        this.weight = weight;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
    }
}
