package com.flightBookingSystem.interfaces;

public interface IFlight {
    String getSource();
    String getDestination();
    String getAirlineName();
    double getCost();
    boolean servesMeals();
}
