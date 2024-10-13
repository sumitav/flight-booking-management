package com.flightBookingSystem.interfaces;

import java.util.List;

import com.flightBookingSystem.entities.Flight;

public interface IFlightDAO {
    void addFlight(Flight flight);
    List<Flight> getFlightsFrom(String source);
    List<Flight> getAllFlights();
}
