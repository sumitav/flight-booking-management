package com.flightBookingSystem.interfaces;

public interface IAirline {
    void registerFlight(String source, String destination, double cost, boolean servesMeals);
}