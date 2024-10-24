package com.flightBookingSystem.services;

import com.flightBookingSystem.entities.Airline;

public interface airlineManagerService {
    void registerFlight(Airline airline, String source, String destination, double cost, boolean servesMeals);
}
