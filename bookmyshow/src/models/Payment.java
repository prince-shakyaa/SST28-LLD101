package models;

import enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

    private final String paymentId;
    private final int bookingId;
    private final double amount;
    private PaymentStatus paymentStatus;
    private final LocalDateTime paymentTime;
    private String transactionRef;

    public Payment(int bookingId, double amount) {
        this.paymentId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentStatus = PaymentStatus.PENDING;
        this.paymentTime = LocalDateTime.now();
    }

    public boolean processPayment() {
        this.transactionRef = "TXN-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.paymentStatus = PaymentStatus.PAID;
        System.out.println("[Payment] Amount Rs." + amount + " paid. Ref: " + transactionRef);
        return true;
    }

    public void refund() {
        this.paymentStatus = PaymentStatus.REFUNDED;
        System.out.println("[Payment] Refund processed for payment " + paymentId + ". Amount: Rs." + amount);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    @Override
    public String toString() {
        return "Payment{id='" + paymentId + "', bookingId=" + bookingId + ", amount=Rs." + amount
                + ", status=" + paymentStatus + ", ref='" + transactionRef + "'}";
    }
}
