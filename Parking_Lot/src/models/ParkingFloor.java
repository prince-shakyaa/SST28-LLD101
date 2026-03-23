package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one floor (level) of the parking lot.
 */
public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }

    public int getFloorNumber()       { return floorNumber; }
    public List<ParkingSlot> getSlots() { return slots; }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }
}
