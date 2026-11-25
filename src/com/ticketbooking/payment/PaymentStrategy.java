package com.ticketbooking.payment;

public interface PaymentStrategy {
    boolean pay(double amount);
}
