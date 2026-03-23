package strategy;

import models.Bill;
import models.ParkingTicket;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Calculates parking charges based on the allocated slot type's hourly rate.
 * Duration is rounded UP to the nearest hour (minimum 1 hour).
 * Billing is on slot type, NOT vehicle type.
 */
public class HourlyPricingStrategy implements PricingStrategy {

    @Override
    public Bill calculateBill(ParkingTicket ticket, LocalDateTime exitTime) {
        long totalMinutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        if (totalMinutes < 0) totalMinutes = 0;

        // Round up to the nearest hour (minimum 1 hour)
        long billableHours = Math.max(1, (totalMinutes + 59) / 60);

        double rate = ticket.getAllocatedSlot().getSlotType().getHourlyRate();
        double totalAmount = billableHours * rate;

        return new Bill(ticket, exitTime, totalMinutes, totalAmount);
    }
}
