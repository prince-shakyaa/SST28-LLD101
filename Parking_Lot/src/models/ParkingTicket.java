package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ParkingTicket {
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot allocatedSlot;
    private final String entryGateId;
    private final LocalDateTime entryTime;

    public ParkingTicket(Vehicle vehicle, ParkingSlot allocatedSlot,
                         String entryGateId, LocalDateTime entryTime) {
        this.ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle = vehicle;
        this.allocatedSlot = allocatedSlot;
        this.entryGateId = entryGateId;
        this.entryTime = entryTime;
    }

    public String getTicketId()          { return ticketId; }
    public Vehicle getVehicle()          { return vehicle; }
    public ParkingSlot getAllocatedSlot() { return allocatedSlot; }
    public String getEntryGateId()       { return entryGateId; }
    public LocalDateTime getEntryTime()  { return entryTime; }

    @Override
    public String toString() {
        return "\n========== PARKING TICKET ==========\n" +
               "  Ticket ID   : " + ticketId + "\n" +
               "  Vehicle     : " + vehicle + "\n" +
               "  Slot ID     : " + allocatedSlot.getSlotId() + "\n" +
               "  Slot Type   : " + allocatedSlot.getSlotType() + "\n" +
               "  Floor       : " + allocatedSlot.getFloorNumber() + "\n" +
               "  Entry Gate  : " + entryGateId + "\n" +
               "  Entry Time  : " + entryTime.format(FMT) + "\n" +
               "====================================";
    }
}
