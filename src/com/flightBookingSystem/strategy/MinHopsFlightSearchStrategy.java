package com.flightBookingSystem.strategy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.flightBookingSystem.constants.constants;
import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Flight;
import com.flightBookingSystem.services.flightRouteBuilder;

public class MinHopsFlightSearchStrategy implements FlightSearchStrategy {
    private final FlightDAO flightDAO;

    public MinHopsFlightSearchStrategy(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

     @Override
    public List<Flight> searchFlights(String source, String destination, boolean requiresMeals) {
        Map<String, Flight> prevFlightMap = new HashMap<>();
        Map<String, Integer> hopMap = new HashMap<>(); // Track the number of hops to each destination
        Map<String, Double> costMap = new HashMap<>(); // Track the minimum cost to each destination
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        queue.add(source);
        hopMap.put(source, 0);
        costMap.put(source, 0.0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destination)) {
                break;
            }

            visited.add(current);

            for (Flight flight : flightDAO.getFlightsFrom(current)) {
                String nextDestination = flight.getDestination();
                
                // Skip visited destinations
                if (visited.contains(nextDestination)) continue;

                // Meal requirements
                if (requiresMeals && !flight.servesMeals()) continue;

                // Skip meal-serving airlines if meals are not required
                if (!requiresMeals && flight.getAirlineName().equalsIgnoreCase(constants.MEAL_SERVING_AIRLINE)) {
                    continue;
                }

                double newCost = costMap.getOrDefault(current, Double.MAX_VALUE) + flight.getCost();
                int newHops = hopMap.getOrDefault(current, 0) + 1;

                // Check if the destination is not visited or has fewer hops, or if the cost is lower for the same hops
                if (!hopMap.containsKey(nextDestination) || 
                   newHops < hopMap.get(nextDestination) || 
                   (newHops == hopMap.get(nextDestination) && newCost < costMap.get(nextDestination))) {

                    prevFlightMap.put(nextDestination, flight);
                    hopMap.put(nextDestination, newHops);
                    costMap.put(nextDestination, newCost);
                    queue.add(nextDestination);
                }
            }
        }

        return flightRouteBuilder.buildRoute(source, destination, prevFlightMap);
    }
}