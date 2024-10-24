package com.flightBookingSystem.strategy;

import java.util.List;

import com.flightBookingSystem.entities.Flight;

public interface FlightSearchStrategy {
    List<Flight> searchFlights(String source, String destination, boolean requiresMeals);
}
