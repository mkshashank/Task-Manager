package com.wipro.java.ms;

public class RestaurantSRPExample {
    public static void main(String[] args) {
        OrderManager order = new OrderManager("Pasta");
        order.takeOrder();

        Chef chef = new Chef();
        chef.cookFood(order.getDish());

        PaymentProcessor payment = new PaymentProcessor(599);
        payment.processPayment();
    }
}
