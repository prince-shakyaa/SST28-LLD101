import enums.SlotType;
import enums.VehicleType;
import models.*;
import strategy.HourlyPricingStrategy;
import strategy.NearestSlotStrategy;
import strategy.PricingStrategy;
import strategy.SlotAssignmentStrategy;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton ParkingLot — the central controller.
 *
 * Exposes:
 *   park(vehicleDetails, entryTime, requestedSlotType, entryGateID)  → ParkingTicket
 *   status()                                                          → void (prints to console)
 *   exit(parkingTicket, exitTime)                                     → Bill
 */
public class ParkingLot {

    // ── Singleton ────────────────────────────────────────────────────────────────
    private static ParkingLot instance;

    public static ParkingLot getInstance(String name, int numGates) {
        if (instance == null) {
            instance = new ParkingLot(name, numGates);
        }
        return instance;
    }

    /** Reset singleton (useful for testing). */
    public static void resetInstance() { instance = null; }

    // ── State ─────────────────────────────────────────────────────────────────────
    private final String name;
    private final List<ParkingFloor> floors;
    private final Map<String, EntryGate> gates;      // gateId → EntryGate
    private final int numGates;

    private final SlotAssignmentStrategy slotStrategy;
    private final PricingStrategy pricingStrategy;

    // All slots flattened for quick lookup
    private final List<ParkingSlot> allSlots;

    // Active tickets: ticketId → ParkingTicket
    private final Map<String, ParkingTicket> activeTickets;

    // ── Constructor ───────────────────────────────────────────────────────────────
    private ParkingLot(String name, int numGates) {
        this.name = name;
        this.numGates = numGates;
        this.floors = new ArrayList<>();
        this.gates = new LinkedHashMap<>();
        this.allSlots = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        this.slotStrategy = new NearestSlotStrategy();
        this.pricingStrategy = new HourlyPricingStrategy();
    }

    // ── Setup helpers (called once during initialization) ─────────────────────────

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
        allSlots.addAll(floor.getSlots());
    }

    public void addGate(EntryGate gate) {
        gates.put(gate.getGateId(), gate);
    }

    // ── Core APIs ──────────────────────────────────────────────────────────────────

    /**
     * Park a vehicle.
     *
     * @param vehicle         vehicle details
     * @param entryTime       time of entry
     * @param requestedType   preferred slot type
     * @param entryGateId     the ID of the entry gate used
     * @return generated ParkingTicket, or null if no slot available
     */
    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime,
                               SlotType requestedType, String entryGateId) {

        EntryGate gate = gates.get(entryGateId);
        if (gate == null) {
            System.out.println("❌ Unknown gate: " + entryGateId);
            return null;
        }

        ParkingSlot slot = slotStrategy.findSlot(
                allSlots, vehicle.getVehicleType(), requestedType, gate.getGateIndex());

        if (slot == null) {
            System.out.println("❌ No available slot for " + vehicle + " via " + gate);
            return null;
        }

        slot.occupy();
        ParkingTicket ticket = new ParkingTicket(vehicle, slot, entryGateId, entryTime);
        activeTickets.put(ticket.getTicketId(), ticket);

        System.out.println("✅ Vehicle parked successfully." + ticket);
        return ticket;
    }

    /**
     * Return current slot availability grouped by slot type.
     */
    public void status() {
        System.out.println("\n======= PARKING LOT STATUS: " + name + " =======");
        for (SlotType type : SlotType.values()) {
            long total = allSlots.stream().filter(s -> s.getSlotType() == type).count();
            long free  = allSlots.stream().filter(s -> s.getSlotType() == type && !s.isOccupied()).count();
            System.out.printf("  %-8s: %d / %d available%n", type, free, total);
        }
        System.out.println("==============================================\n");
    }

    /**
     * Process vehicle exit.
     *
     * @param ticket   the ticket issued at entry
     * @param exitTime the exit time
     * @return generated Bill
     */
    public Bill exit(ParkingTicket ticket, LocalDateTime exitTime) {
        if (!activeTickets.containsKey(ticket.getTicketId())) {
            System.out.println("❌ Invalid or already processed ticket: " + ticket.getTicketId());
            return null;
        }

        // Release the slot
        ticket.getAllocatedSlot().release();
        activeTickets.remove(ticket.getTicketId());

        Bill bill = pricingStrategy.calculateBill(ticket, exitTime);
        System.out.println("✅ Vehicle exited. " + bill);
        return bill;
    }

    // ── Accessors ──────────────────────────────────────────────────────────────────
    public List<ParkingFloor> getFloors() { return floors; }
    public String getName()               { return name; }
}
