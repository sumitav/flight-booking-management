package com.flightBookingSystem.strategy;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.flightBookingSystem.constants.constants;
import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Flight;
import com.flightBookingSystem.services.flightRouteBuilder;

public class CheapestFlightSearchStrategy implements FlightSearchStrategy {
    private final FlightDAO flightDAO;

    public CheapestFlightSearchStrategy(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public List<Flight> searchFlights(String source, String destination, boolean requiresMeals) {
        Map<String, Double> costMap = new HashMap<>();
        Map<String, Integer> hopMap = new HashMap<>();
        Map<String, Flight> prevFlightMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(costMap::get));

        costMap.put(source, 0.0);
        queue.add(source);
        hopMap.put(source, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destination)) {
                break;
            }

            visited.add(current);

            for (Flight flight : flightDAO.getFlightsFrom(current)) {
                String nextDestination = flight.getDestination();
                if (visited.contains(nextDestination)) continue;
                if (requiresMeals && !flight.servesMeals()) continue;
               if (!requiresMeals && flight.getAirlineName().equalsIgnoreCase(constants.MEAL_SERVING_AIRLINE)) {
                    continue;
                }
                double newCost = costMap.getOrDefault(current, Double.MAX_VALUE) + flight.getCost();
                int newHops = hopMap.getOrDefault(current, 0) + 1;
                if (!costMap.containsKey(nextDestination) || 
                    newCost < costMap.get(nextDestination) || 
                    (newCost == costMap.get(nextDestination) && newHops < hopMap.get(nextDestination))) {

                    costMap.put(nextDestination, newCost);
                    hopMap.put(nextDestination, newHops);
                    prevFlightMap.put(nextDestination, flight);
                    queue.add(nextDestination);
                }
            }
        }

        return flightRouteBuilder.buildRoute(source, destination, prevFlightMap);
    }
}
