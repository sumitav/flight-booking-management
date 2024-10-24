package com.flightBookingSystem.services;

import java.util.*;
import com.flightBookingSystem.entities.Flight;
import com.flightBookingSystem.strategy.FlightSearchStrategy;

public class flightSearchServiceImpl implements flightSearchService {
    private FlightSearchStrategy searchStrategy;
     public void setSearchStrategy(FlightSearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }


    @Override
    public void searchFlights(String source, String destination, boolean requiresMeals) {
        List<Flight> route = searchStrategy.searchFlights(source, destination, requiresMeals);
        displayRoute(route);
    }

    private void displayRoute(List<Flight> route) {
        if (route == null || route.isEmpty()) {
            System.out.println("No route found.");
            return;
        }
        for (Flight flight : route) {
            System.out.println(flight.getSource() + " to " + flight.getDestination() +
                    " via " + flight.getAirlineName() + " for " + flight.getCost());
        }
        System.out.println("Total Flights=" + route.size());
        System.out.println("Total Cost=" + calculateTotalCost(route));
    }

    private double calculateTotalCost(List<Flight> route) {
        return route.stream().mapToDouble(Flight::getCost).sum();
    }
}
