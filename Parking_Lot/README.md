# 🅿️ Multilevel Parking Lot — LLD Design & Implementation

## Design & Approach

### Problem Summary
A multilevel parking lot serving 2-wheelers, cars, and buses across multiple floors and entry gates. The system assigns the **nearest compatible slot** to each vehicle, generates tickets on entry, and bills on exit based on the **slot type** (not vehicle type).

---

## Design Decisions

| Decision | Choice | Reason |
|---|---|---|
| Creational Pattern | **Singleton** for `ParkingLot` | Only one lot instance should exist |
| Behavioral Pattern | **Strategy** for Pricing & Slot Assignment | Open/Closed Principle — swap algorithms without touching core |
| Slot compatibility | Smaller in larger OK, never downgrade | Per requirements |
| Billing unit | Rounded up to nearest hour, minimum 1 hr | Fair billing practice |

### Rates
| Slot Type | Vehicle | Rate |
|---|---|---|
| SMALL | 2-Wheeler | ₹20/hr |
| MEDIUM | Car | ₹40/hr |
| LARGE | Bus | ₹80/hr |

### Slot Compatibility Matrix
| Vehicle ↓ \ Slot → | SMALL | MEDIUM | LARGE |
|---|---|---|---|
| TWO_WHEELER | ✅ | ✅ | ✅ |
| CAR | ❌ | ✅ | ✅ |
| BUS | ❌ | ❌ | ✅ |

---

## Class Diagram

```mermaid
classDiagram

    class VehicleType {
        <<enumeration>>
        TWO_WHEELER
        CAR
        BUS
    }

    class SlotType {
        <<enumeration>>
        SMALL
        MEDIUM
        LARGE
        +double hourlyRate
        +getHourlyRate() double
    }

    class Vehicle {
        -String licensePlate
        -VehicleType vehicleType
        +getLicensePlate() String
        +getVehicleType() VehicleType
    }

    class ParkingSlot {
        -String slotId
        -int floorNumber
        -int slotNumber
        -SlotType slotType
        -int[] distanceFromGate
        -boolean isOccupied
        +occupy() void
        +release() void
        +getDistanceFromGate(int gateIndex) int
        +isOccupied() boolean
    }

    class ParkingTicket {
        -String ticketId
        -Vehicle vehicle
        -ParkingSlot allocatedSlot
        -String entryGateId
        -LocalDateTime entryTime
        +getTicketId() String
        +getVehicle() Vehicle
        +getAllocatedSlot() ParkingSlot
        +getEntryTime() LocalDateTime
    }

    class Bill {
        -ParkingTicket ticket
        -LocalDateTime exitTime
        -long durationMinutes
        -double totalAmount
        +getTotalAmount() double
    }

    class EntryGate {
        -String gateId
        -String gateName
        -int gateIndex
        +getGateId() String
        +getGateIndex() int
    }

    class ParkingFloor {
        -int floorNumber
        -List~ParkingSlot~ slots
        +addSlot(ParkingSlot) void
        +getSlots() List~ParkingSlot~
    }

    class PricingStrategy {
        <<interface>>
        +calculateBill(ParkingTicket, LocalDateTime) Bill
    }

    class HourlyPricingStrategy {
        +calculateBill(ParkingTicket, LocalDateTime) Bill
    }

    class SlotAssignmentStrategy {
        <<interface>>
        +findSlot(List~ParkingSlot~, VehicleType, SlotType, int) ParkingSlot
    }

    class NearestSlotStrategy {
        +findSlot(List~ParkingSlot~, VehicleType, SlotType, int) ParkingSlot
        -getAcceptableTypes(VehicleType, SlotType) List~SlotType~
    }

    class ParkingLot {
        <<singleton>>
        -String name
        -List~ParkingFloor~ floors
        -Map~String,EntryGate~ gates
        -List~ParkingSlot~ allSlots
        -Map~String,ParkingTicket~ activeTickets
        -SlotAssignmentStrategy slotStrategy
        -PricingStrategy pricingStrategy
        +getInstance(String, int) ParkingLot
        +addFloor(ParkingFloor) void
        +addGate(EntryGate) void
        +park(Vehicle, LocalDateTime, SlotType, String) ParkingTicket
        +status() void
        +exit(ParkingTicket, LocalDateTime) Bill
    }

    Vehicle --> VehicleType
    ParkingSlot --> SlotType
    ParkingTicket --> Vehicle
    ParkingTicket --> ParkingSlot
    Bill --> ParkingTicket
    ParkingFloor --> ParkingSlot
    HourlyPricingStrategy ..|> PricingStrategy
    NearestSlotStrategy ..|> SlotAssignmentStrategy
    ParkingLot --> ParkingFloor
    ParkingLot --> EntryGate
    ParkingLot --> PricingStrategy
    ParkingLot --> SlotAssignmentStrategy
```

---

## Project Structure

```
Parking_Lot/
├── src/
│   ├── enums/
│   │   ├── VehicleType.java
│   │   └── SlotType.java
│   ├── models/
│   │   ├── Vehicle.java
│   │   ├── ParkingSlot.java
│   │   ├── ParkingTicket.java
│   │   ├── Bill.java
│   │   ├── ParkingFloor.java
│   │   └── EntryGate.java
│   ├── strategy/
│   │   ├── PricingStrategy.java
│   │   ├── HourlyPricingStrategy.java
│   │   ├── SlotAssignmentStrategy.java
│   │   └── NearestSlotStrategy.java
│   ├── ParkingLot.java
│   └── Main.java
└── README.md
```

---

## How to Run

```bash
# Compile
mkdir -p out
javac -d out src/enums/*.java src/models/*.java src/strategy/*.java src/ParkingLot.java src/Main.java

# Run
java -cp out Main
```

---

## API Reference

### `park(vehicle, entryTime, requestedSlotType, entryGateId)`
Parks a vehicle. Returns a `ParkingTicket` with vehicle details, assigned slot, slot type, and entry time. Auto-upgrades to next larger compatible slot if requested type is unavailable.

### `status()`
Prints the current availability (free / total) for each slot type (SMALL, MEDIUM, LARGE).

### `exit(ticket, exitTime)`
Releases the slot, calculates bill based on **slot type rate × hours** (rounded up, min 1 hr), and returns the `Bill`.
