# SST28-LLD101 — SOLID Refactoring Assignment

> **[LLD 101: Assignment Submission] SOLID Refactoring Assignment**  
> Scaler School of Technology — Low Level Design 101

---

## Overview

This repository contains **6 behavior-preserving refactoring exercises** focused on the **SOLID design principles**. Each exercise starts with working but poorly designed code, and is refactored step-by-step using only core OOP mechanisms — classes, interfaces, composition, polymorphism, and encapsulation.

---

## SOLID Principles Covered

| Principle | Full Name | Exercises |
|-----------|-----------|-----------|
| **S** | Single Responsibility Principle (SRP) | Ex1, Ex2 |
| **O** | Open/Closed Principle (OCP) | Ex3, Ex4 |
| **L** | Liskov Substitution Principle (LSP) | Ex5, Ex6 |

---

## Exercises

### Ex1 — SRP: Student Onboarding Registration
**Problem:** `OnboardingService` was a god class mixing parsing, validation, ID generation, persistence, and printing.  
**Refactor:** Extracted dedicated classes — `InputParser`, `StudentValidator`, `StudentRepository` (interface), `FakeDb` (implementation), `ConsolePrinter` — each with a single clear responsibility.

### Ex2 — SRP: Cafeteria Billing System
**Problem:** `CafeteriaSystem` mixed discount logic, tax rules, invoice formatting, and file persistence in one place.  
**Refactor:** Separated into `TaxCalculator`, `DiscountCalculator`, `InvoiceFormatter` (interface), `ConsoleInvoiceFormatter`, and `InvoiceStore` (interface).

### Ex3 — OCP: Placement Eligibility Engine
**Problem:** `EligibilityEngine` used a long chain of `if` statements for each rule (CGR, attendance, credits, disciplinary), requiring edits every time a new rule was added.  
**Refactor:** Introduced an `EligibilityRule` interface with implementations `CgrRule`, `AttendanceRule`, `CreditsRule`, `DisciplinaryRule`. The engine now accepts a `List<EligibilityRule>` — new rules can be added without touching existing code.

### Ex4 — OCP: Hostel Fee Calculator
**Problem:** `HostelFeeCalculator` had a `switch` on room types and repeated `if/else` chains for add-ons.  
**Refactor:** Introduced a `FeeComponent` interface with `RoomFee` and `AddonFee` implementations. The calculator now sums components from a list — new room types and add-ons require zero edits to the core algorithm. Separated `BookingProcessor` to handle printing and persistence.

### Ex5 — LSP: File Exporter Hierarchy
**Problem:** `PdfExporter` threw exceptions for large inputs (tightened preconditions), `CsvExporter` silently corrupted data, and `JsonExporter` handled nulls inconsistently — all substitutability violations.  
**Refactor:** Made `Exporter` an abstract class with a null-guard template method. Used composition (`SizeConstrainedExporter`, `SanitizingExporter`) as decorators instead of inheritance to enforce constraints. No subtype tightens the base contract.

### Ex6 — LSP: Notification Sender Inheritance
**Problem:** `WhatsAppSender` threw exceptions for non-E.164 numbers, `EmailSender` silently truncated messages, and `SmsSender` ignored fields — all callers needed subtype-specific workarounds.  
**Refactor:** Defined a uniform `NotificationSender` contract. Moved phone validation to a dedicated `PhoneValidator` helper, message normalization to `StringNormalizer`. Concrete senders are faithful substituents of the base type.

---

## How to Run Any Exercise

```bash
cd SOLID/Ex<N>/src
javac *.java
java Main
```

No Maven/Gradle. **Java 17.** Default package (no `package` lines).

---

## Project Structure

```
SST28-LLD101/
└── SOLID/
    ├── Ex1/src/   ← SRP: Student Onboarding
    ├── Ex2/src/   ← SRP: Cafeteria Billing
    ├── Ex3/src/   ← OCP: Eligibility Engine
    ├── Ex4/src/   ← OCP: Hostel Fee Calculator
    ├── Ex5/src/   ← LSP: File Exporters
    └── Ex6/src/   ← LSP: Notification Senders
```

---

## Constraints Followed

- No design patterns used — only pure OOP mechanisms
- No external libraries
- All programs preserve exact original console output
- Default package only (no `package` declarations)
