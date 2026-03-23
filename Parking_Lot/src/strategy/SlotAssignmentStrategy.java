package strategy;

import enums.SlotType;
import enums.VehicleType;
import models.ParkingSlot;

import java.util.List;

public interface SlotAssignmentStrategy {
    /**
     * Find the nearest available compatible slot for a vehicle from a given gate.
     *
     * @param allSlots        all slots in the parking lot (across all floors)
     * @param vehicleType     the type of vehicle requesting a slot
     * @param requestedType   the preferred slot type (may be upgraded if unavailable)
     * @param gateIndex       the index of the entry gate (0-based)
     * @return the best ParkingSlot, or null if none available
     */
    ParkingSlot findSlot(List<ParkingSlot> allSlots, VehicleType vehicleType,
                          SlotType requestedType, int gateIndex);
}
