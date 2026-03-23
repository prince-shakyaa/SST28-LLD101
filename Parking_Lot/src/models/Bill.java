package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ParkingTicket ticket;
    private final LocalDateTime exitTime;
    private final long durationMinutes;
    private final double totalAmount;

    public Bill(ParkingTicket ticket, LocalDateTime exitTime,
                long durationMinutes, double totalAmount) {
        this.ticket = ticket;
        this.exitTime = exitTime;
        this.durationMinutes = durationMinutes;
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount()       { return totalAmount; }
    public ParkingTicket getTicket()     { return ticket; }
    public LocalDateTime getExitTime()   { return exitTime; }
    public long getDurationMinutes()     { return durationMinutes; }

    @Override
    public String toString() {
        long hours = durationMinutes / 60;
        long mins  = durationMinutes % 60;
        return "\n============== BILL ================\n" +
               "  Ticket ID   : " + ticket.getTicketId() + "\n" +
               "  Vehicle     : " + ticket.getVehicle() + "\n" +
               "  Slot ID     : " + ticket.getAllocatedSlot().getSlotId() + "\n" +
               "  Slot Type   : " + ticket.getAllocatedSlot().getSlotType() + "\n" +
               "  Entry Time  : " + ticket.getEntryTime().format(FMT) + "\n" +
               "  Exit Time   : " + exitTime.format(FMT) + "\n" +
               "  Duration    : " + hours + " hr " + mins + " min\n" +
               "  Rate        : ₹" + ticket.getAllocatedSlot().getSlotType().getHourlyRate() + "/hr\n" +
               "  Total Amount: ₹" + String.format("%.2f", totalAmount) + "\n" +
               "====================================";
    }
}
