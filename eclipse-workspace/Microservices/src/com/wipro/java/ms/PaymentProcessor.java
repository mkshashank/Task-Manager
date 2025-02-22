package com.wipro.java.ms;

class PaymentProcessor {
    private double amount;

    public PaymentProcessor(double amount) {
        this.amount = amount;
    }

    public void processPayment() {
        System.out.println("Payment of Rs." + amount + " processed");
    }
}

