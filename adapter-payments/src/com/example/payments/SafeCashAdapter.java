package com.example.payments;

import java.util.Objects;

/**
 * Adapter: wraps SafeCashClient to implement PaymentGateway.
 * SafeCashClient.createPayment(amount, user) → SafeCashPayment.confirm()
 */
public class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient client;

    public SafeCashAdapter(SafeCashClient client) {
        this.client = Objects.requireNonNull(client, "client");
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId, "customerId");
        SafeCashPayment payment = client.createPayment(amountCents, customerId);
        return payment.confirm();
    }
}
