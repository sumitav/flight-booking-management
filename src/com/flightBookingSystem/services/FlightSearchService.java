package com.flightBookingSystem.services;
public interface flightSearchService {
    void searchFlights(String source, String destination, boolean requiresMeals);
}