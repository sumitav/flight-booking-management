package com.flightBookingSystem.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.flightBookingSystem.entities.Flight;

public class flightRouteBuilder{
    public static List<Flight> buildRoute(String source, String destination, Map<String, Flight> prevFlightMap) {
        List<Flight> route = new ArrayList<>();
        String current = destination;

        while (prevFlightMap.containsKey(current)) {
            Flight flight = prevFlightMap.get(current);
            route.add(0, flight); // Add at the beginning to reverse the path
            current = flight.getSource();
        }

        if (!current.equals(source)) {
            return Collections.emptyList(); // No valid route found
        }

        return route;
    }
}
