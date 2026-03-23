package models;

import enums.SlotType;

/**
 * Represents a single parking slot in the lot.
 * distanceFromGate[gateIndex] gives the distance from that entry gate
 * (e.g., physical row number or distance metric).
 */
public class ParkingSlot {
    private final String slotId;        // e.g. "L1-S3"
    private final int floorNumber;
    private final int slotNumber;
    private final SlotType slotType;
    private final int[] distanceFromGate; // index = gateIndex
    private boolean isOccupied;

    public ParkingSlot(String slotId, int floorNumber, int slotNumber,
                       SlotType slotType, int[] distanceFromGate) {
        this.slotId = slotId;
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.distanceFromGate = distanceFromGate;
        this.isOccupied = false;
    }

    public String getSlotId()       { return slotId; }
    public int getFloorNumber()     { return floorNumber; }
    public int getSlotNumber()      { return slotNumber; }
    public SlotType getSlotType()   { return slotType; }
    public boolean isOccupied()     { return isOccupied; }

    public int getDistanceFromGate(int gateIndex) {
        return distanceFromGate[gateIndex];
    }

    public void occupy()  { this.isOccupied = true; }
    public void release() { this.isOccupied = false; }

    @Override
    public String toString() {
        return slotId + "[" + slotType + ", floor=" + floorNumber +
               ", slot=" + slotNumber + ", occupied=" + isOccupied + "]";
    }
}
