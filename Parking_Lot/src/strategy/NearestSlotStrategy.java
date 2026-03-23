package strategy;

import enums.SlotType;
import enums.VehicleType;
import models.ParkingSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Assigns the nearest available compatible slot.
 *
 * Compatibility rules:
 *   TWO_WHEELER → SMALL, MEDIUM, LARGE   (in preference order)
 *   CAR         → MEDIUM, LARGE
 *   BUS         → LARGE only
 *
 * If the requested slot type is available the system uses it;
 * otherwise it auto-upgrades to the next larger type.
 * "Nearest" = smallest distanceFromGate value among candidates.
 */
public class NearestSlotStrategy implements SlotAssignmentStrategy {

    @Override
    public ParkingSlot findSlot(List<ParkingSlot> allSlots, VehicleType vehicleType,
                                 SlotType requestedType, int gateIndex) {

        // Build ordered list of acceptable slot types (requested first, then larger ones)
        List<SlotType> acceptable = getAcceptableTypes(vehicleType, requestedType);

        for (SlotType type : acceptable) {
            ParkingSlot best = null;
            int bestDist = Integer.MAX_VALUE;

            for (ParkingSlot slot : allSlots) {
                if (slot.isOccupied() || slot.getSlotType() != type) continue;
                int dist = slot.getDistanceFromGate(gateIndex);
                if (dist < bestDist) {
                    bestDist = dist;
                    best = slot;
                }
            }
            if (best != null) return best; // found nearest of this type
        }
        return null; // no compatible slot available
    }

    /**
     * Returns acceptable slot types for a vehicle, starting from requestedType
     * and going up to larger types only (vehicles cannot downgrade).
     */
    private List<SlotType> getAcceptableTypes(VehicleType vehicleType, SlotType requestedType) {
        List<SlotType> all = new ArrayList<>();
        switch (vehicleType) {
            case TWO_WHEELER:
                all.add(SlotType.SMALL);
                all.add(SlotType.MEDIUM);
                all.add(SlotType.LARGE);
                break;
            case CAR:
                all.add(SlotType.MEDIUM);
                all.add(SlotType.LARGE);
                break;
            case BUS:
                all.add(SlotType.LARGE);
                break;
        }

        // Start from the requested type onwards (no downgrade)
        int startIdx = all.indexOf(requestedType);
        if (startIdx < 0) startIdx = 0; // fallback: use first compatible

        return all.subList(startIdx, all.size());
    }
}
