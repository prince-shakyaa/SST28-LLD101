package strategy;

import models.Bill;
import models.ParkingTicket;

import java.time.LocalDateTime;

public interface PricingStrategy {
    /**
     * Calculate the bill for a parked vehicle.
     *
     * @param ticket   the parking ticket on entry
     * @param exitTime the time of exit
     * @return a Bill containing the total amount due
     */
    Bill calculateBill(ParkingTicket ticket, LocalDateTime exitTime);
}
