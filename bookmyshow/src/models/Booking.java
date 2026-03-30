package models;

import enums.BookingStatus;
import enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Booking {

    private static int idCounter = 1;

    private final int bookingId;
    private final String confirmationId;
    private final int userId;
    private final int showtimeId;
    private final int movieId;
    private final int theaterId;
    private final int screenId;
    private final List<Seat> bookedSeats;
    private double totalAmount;
    private BookingStatus bookingStatus;
    private Payment payment;
    private final LocalDateTime bookedAt;

    public Booking(int userId, int showtimeId, int movieId, int theaterId, int screenId) {
        this.bookingId = idCounter++;
        this.confirmationId = "BMS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.screenId = screenId;
        this.bookedSeats = new ArrayList<>();
        this.totalAmount = 0.0;
        this.bookingStatus = BookingStatus.PENDING;
        this.bookedAt = LocalDateTime.now();
    }

    public void addSeat(Seat seat, double price) {
        bookedSeats.add(seat);
        totalAmount += price;
    }

    public boolean confirmBooking() {
        if (bookedSeats.isEmpty()) {
            System.out.println("[Booking " + bookingId + "] No seats selected. Cannot confirm.");
            return false;
        }
        payment = new Payment(bookingId, totalAmount);
        boolean paid = payment.processPayment();
        if (paid) {
            this.bookingStatus = BookingStatus.CONFIRMED;
            System.out.println("[Booking] Confirmed! Confirmation ID: " + confirmationId);
            return true;
        }
        this.bookingStatus = BookingStatus.PENDING;
        return false;
    }

    public boolean cancelBooking() {
        if (bookingStatus == BookingStatus.CANCELLED) {
            System.out.println("[Booking " + bookingId + "] Already cancelled.");
            return false;
        }
        this.bookingStatus = BookingStatus.CANCELLED;
        if (payment != null && payment.getPaymentStatus() == PaymentStatus.PAID) {
            payment.refund();
        }
        System.out.println("[Booking] Cancelled. Confirmation ID: " + confirmationId);
        return true;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public int getUserId() {
        return userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public int getScreenId() {
        return screenId;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    @Override
    public String toString() {
        return "Booking{id=" + bookingId + ", confirmationId='" + confirmationId + "', userId=" + userId
                + ", showtimeId=" + showtimeId + ", seats=" + bookedSeats.size()
                + ", total=Rs." + totalAmount + ", status=" + bookingStatus + "}";
    }
}
