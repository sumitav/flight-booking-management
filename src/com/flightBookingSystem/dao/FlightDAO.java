package com.flightBookingSystem.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flightBookingSystem.entities.Flight;
import com.flightBookingSystem.interfaces.IFlightDAO;

public class FlightDAO implements IFlightDAO {
    private static FlightDAO instance;
    private Map<String, List<Flight>> flightMap;

    private FlightDAO() {
        this.flightMap = new HashMap<>();
    }
    public static synchronized FlightDAO getInstance() {
        if (instance == null) {
            instance = new FlightDAO();
        }
        return instance;
    }

    @Override
    public void addFlight(Flight flight) {
        flightMap.computeIfAbsent(flight.getSource(), k -> new ArrayList<>()).add(flight);
    }

    @Override
    public List<Flight> getFlightsFrom(String source) {
        return flightMap.getOrDefault(source, new ArrayList<>());
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> allFlights = new ArrayList<>();
        for (List<Flight> flights : flightMap.values()) {
            allFlights.addAll(flights);
        }
        return allFlights;
    }
}