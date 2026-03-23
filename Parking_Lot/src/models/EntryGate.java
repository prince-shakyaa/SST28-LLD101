package models;

/**
 * Represents an entry gate of the parking lot.
 * gateIndex is used to look up distances in ParkingSlot.
 */
public class EntryGate {
    private final String gateId;
    private final String gateName;
    private final int gateIndex; // 0-based index used in distanceFromGate[]

    public EntryGate(String gateId, String gateName, int gateIndex) {
        this.gateId = gateId;
        this.gateName = gateName;
        this.gateIndex = gateIndex;
    }

    public String getGateId()   { return gateId; }
    public String getGateName() { return gateName; }
    public int getGateIndex()   { return gateIndex; }

    @Override
    public String toString() {
        return "Gate[" + gateId + ", " + gateName + "]";
    }
}
