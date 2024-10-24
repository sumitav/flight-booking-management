package com.flightBookingSystem.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flightBookingSystem.entities.Flight;

public class FlightDAO {

    private static class Holder {
        private static final FlightDAO instance = new FlightDAO();
    }

    public static FlightDAO getInstance() {
        return Holder.instance;
    }

    private Map<String, List<Flight>> flightMap;

    private FlightDAO() {
        flightMap = new HashMap<>();
    }

    // Add flight to the flight map
    public void addFlight(Flight flight) {
       System.out.println("Adding flight from " + flight.getSource() + " to " + flight.getDestination() + 
                           ", Airline: " + flight.getAirlineName() + ", Cost: " + flight.getCost() + 
                           ", Serves Meals: " + flight.servesMeals());
        flightMap.computeIfAbsent(flight.getSource(), k -> new ArrayList<>()).add(flight);
    }

    // Retrieve flights based on source
    public List<Flight> getFlightsFrom(String source) {
        
        List<Flight> flights = flightMap.getOrDefault(source, new ArrayList<>());
        
        return flights;
    }
}
