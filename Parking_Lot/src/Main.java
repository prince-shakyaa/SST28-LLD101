import enums.SlotType;
import enums.VehicleType;
import models.*;

import java.time.LocalDateTime;

/**
 * Demo / test harness for the Multilevel Parking Lot system.
 *
 * Setup:
 *   - 2 floors, 2 entry gates
 *   - Floor 1: 3 SMALL, 2 MEDIUM, 1 LARGE
 *   - Floor 2: 2 SMALL, 2 MEDIUM, 2 LARGE
 *
 * Distance table (rows = slots, columns = Gate 0 / Gate 1):
 *   Slots on Floor 1 are closer to Gate 0 (main entrance).
 *   Slots on Floor 2 are closer to Gate 1 (rear entrance).
 */
public class Main {

    public static void main(String[] args) {

        // ── 1. Initialize the parking lot ─────────────────────────────────────────
        ParkingLot lot = ParkingLot.getInstance("City Centre Parking", 2);

        // Entry gates
        EntryGate gate0 = new EntryGate("G1", "Main Entrance", 0);
        EntryGate gate1 = new EntryGate("G2", "Rear Entrance", 1);
        lot.addGate(gate0);
        lot.addGate(gate1);

        // Floor 1 (closer to Gate 0)
        ParkingFloor floor1 = new ParkingFloor(1);
        // distanceFromGate = { gate0dist, gate1dist }
        floor1.addSlot(new ParkingSlot("L1-S1", 1, 1, SlotType.SMALL,  new int[]{1, 10}));
        floor1.addSlot(new ParkingSlot("L1-S2", 1, 2, SlotType.SMALL,  new int[]{2, 11}));
        floor1.addSlot(new ParkingSlot("L1-S3", 1, 3, SlotType.SMALL,  new int[]{3, 12}));
        floor1.addSlot(new ParkingSlot("L1-M1", 1, 4, SlotType.MEDIUM, new int[]{4, 9}));
        floor1.addSlot(new ParkingSlot("L1-M2", 1, 5, SlotType.MEDIUM, new int[]{5, 8}));
        floor1.addSlot(new ParkingSlot("L1-L1", 1, 6, SlotType.LARGE,  new int[]{6, 7}));
        lot.addFloor(floor1);

        // Floor 2 (closer to Gate 1)
        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addSlot(new ParkingSlot("L2-S1", 2, 1, SlotType.SMALL,  new int[]{12, 2}));
        floor2.addSlot(new ParkingSlot("L2-S2", 2, 2, SlotType.SMALL,  new int[]{13, 3}));
        floor2.addSlot(new ParkingSlot("L2-M1", 2, 3, SlotType.MEDIUM, new int[]{10, 4}));
        floor2.addSlot(new ParkingSlot("L2-M2", 2, 4, SlotType.MEDIUM, new int[]{11, 5}));
        floor2.addSlot(new ParkingSlot("L2-L1", 2, 5, SlotType.LARGE,  new int[]{8,  1}));
        floor2.addSlot(new ParkingSlot("L2-L2", 2, 6, SlotType.LARGE,  new int[]{9,  6}));
        lot.addFloor(floor2);

        // ── 2. Initial status ─────────────────────────────────────────────────────
        System.out.println(">>> Initial Status");
        lot.status();

        LocalDateTime base = LocalDateTime.of(2026, 3, 23, 10, 0);

        // ── 3. Park vehicles ──────────────────────────────────────────────────────
        System.out.println(">>> Parking vehicles...\n");

        // Bike via Gate 1 (main) — expects nearest SMALL = L1-S1
        Vehicle bike1 = new Vehicle("MH12-AB1234", VehicleType.TWO_WHEELER);
        ParkingTicket t1 = lot.park(bike1, base, SlotType.SMALL, "G1");

        // Car via Gate 2 (rear) — expects nearest MEDIUM = L2-M1
        Vehicle car1 = new Vehicle("DL8C-XY9999", VehicleType.CAR);
        ParkingTicket t2 = lot.park(car1, base.plusMinutes(5), SlotType.MEDIUM, "G2");

        // Bus via Gate 1 — expects nearest LARGE = L1-L1
        Vehicle bus1 = new Vehicle("KA01-TT0001", VehicleType.BUS);
        ParkingTicket t3 = lot.park(bus1, base.plusMinutes(10), SlotType.LARGE, "G1");

        // Bike requesting SMALL via Gate 1 — fill all SMALL slots
        Vehicle bike2 = new Vehicle("MH12-AB5678", VehicleType.TWO_WHEELER);
        ParkingTicket t4 = lot.park(bike2, base.plusMinutes(15), SlotType.SMALL, "G1");

        Vehicle bike3 = new Vehicle("MH12-AB9999", VehicleType.TWO_WHEELER);
        ParkingTicket t5 = lot.park(bike3, base.plusMinutes(20), SlotType.SMALL, "G1");

        Vehicle bike4 = new Vehicle("MH12-CD1111", VehicleType.TWO_WHEELER);
        ParkingTicket t6 = lot.park(bike4, base.plusMinutes(25), SlotType.SMALL, "G2");

        Vehicle bike5 = new Vehicle("MH12-CD2222", VehicleType.TWO_WHEELER);
        ParkingTicket t7 = lot.park(bike5, base.plusMinutes(30), SlotType.SMALL, "G2");

        // All SMALL slots now occupied — this bike should auto-upgrade to MEDIUM
        Vehicle bike6 = new Vehicle("MH12-CD3333", VehicleType.TWO_WHEELER);
        System.out.println("\n>>> Parking bike when all SMALL slots are full (auto-upgrade expected):");
        ParkingTicket t8 = lot.park(bike6, base.plusMinutes(35), SlotType.SMALL, "G1");

        // ── 4. Status after parking ───────────────────────────────────────────────
        System.out.println(">>> Status after parking vehicles:");
        lot.status();

        // ── 5. Exit vehicles ──────────────────────────────────────────────────────
        System.out.println(">>> Processing exits...\n");

        // Bike1 exits after 1h30m → billed 2 hours SMALL = 2 * 20 = ₹40
        lot.exit(t1, base.plusHours(1).plusMinutes(30));

        // Car1 exits after 45min → billed 1 hour MEDIUM = 1 * 40 = ₹40
        lot.exit(t2, base.plusHours(0).plusMinutes(50));

        // Bus1 exits after 3h10m → billed 4 hours LARGE = 4 * 80 = ₹320
        lot.exit(t3, base.plusHours(3).plusMinutes(10));

        // Bike6 (in MEDIUM slot) exits after 2h → billed 2 hours MEDIUM = 2 * 40 = ₹80
        if (t8 != null) {
            lot.exit(t8, base.plusHours(2));
        }

        // ── 6. Final status ───────────────────────────────────────────────────────
        System.out.println(">>> Final Status (after exits):");
        lot.status();
    }
}
