package com.wipro.java.ms;

class OrderManager {
    private String dish;

    public OrderManager(String dish) {
        this.dish = dish;
    }

    public void takeOrder() {
        System.out.println("Order placed for: " + dish);
    }

    public String getDish() {
        return dish;
    }
}

