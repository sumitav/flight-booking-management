package com.flightBookingSystem.interfaces;
public interface IFlightSearchService {
    void searchFlights(String source, String destination, boolean requiresMeals);
}