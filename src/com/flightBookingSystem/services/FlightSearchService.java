package com.flightBookingSystem.services;

import java.util.*;

import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Flight;
import com.flightBookingSystem.interfaces.IFlightSearchService;

public class FlightSearchService implements IFlightSearchService {
    private FlightDAO flightDAO;

    public FlightSearchService(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }

    @Override
    public void searchFlights(String source, String destination, boolean requiresMeals) {
        List<Flight> minHopsRoute = findMinHopsRoute(source, destination, requiresMeals);

        List<Flight> cheapestRoute = findCheapestRoute(source, destination, requiresMeals);

        System.out.println("Route with minimum hops:");
        displayRoute(minHopsRoute);

        System.out.println("\nCheapest Route:");
        displayRoute(cheapestRoute);
    }
    /**
     * 
     * @param source
     * @param destination
     * @param requiresMeals
     * @return
     */

    private List<Flight> findMinHopsRoute(String source, String destination, boolean requiresMeals) {
        Map<String, Flight> prevFlightMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Double> costMap = new HashMap<>();
        queue.add(source);
        costMap.put(source, 0.0);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destination)) {
                break;
            }

            visited.add(current);

            for (Flight flight : flightDAO.getFlightsFrom(current)) {
                if (visited.contains(flight.getDestination())) continue;
                if (requiresMeals && !flight.servesMeals()) continue;
                if (!requiresMeals && flight.getAirlineName().equalsIgnoreCase("Indigo")) {
                    continue; // Skip Indigo if meals are not required
                }
                double newCost = costMap.get(current) + flight.getPrice();
                if (!costMap.containsKey(flight.getDestination()) || newCost < costMap.get(flight.getDestination())) {
                    prevFlightMap.put(flight.getDestination(), flight);
                    costMap.put(flight.getDestination(), newCost);
                    queue.add(flight.getDestination());
                }
            }
        }

        return buildRouteFromPrevMap(source, destination, prevFlightMap);
    }
    /**
     * 
     * @param source
     * @param destination
     * @param requiresMeals
     * @return
     */

    private List<Flight> findCheapestRoute(String source, String destination, boolean requiresMeals) {
        Map<String, Double> costMap = new HashMap<>();
        Map<String, Flight> prevFlightMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(costMap::get));

        costMap.put(source, 0.0);
        queue.add(source);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(destination)) {
                break;
            }

            visited.add(current);

            for (Flight flight : flightDAO.getFlightsFrom(current)) {
                if (visited.contains(flight.getDestination())) continue;
                if (requiresMeals && !flight.servesMeals()) continue;
                if (!requiresMeals && flight.getAirlineName().equalsIgnoreCase("Indigo")) {
                    continue; // Skip Indigo if meals are not required
                }
                double newCost = costMap.getOrDefault(current, Double.MAX_VALUE) + flight.getCost();
                if (newCost < costMap.getOrDefault(flight.getDestination(), Double.MAX_VALUE)) {
                    costMap.put(flight.getDestination(), newCost);
                    prevFlightMap.put(flight.getDestination(), flight);
                    queue.add(flight.getDestination());
                }
            }
        }

        return buildRouteFromPrevMap(source, destination, prevFlightMap);
    }
    /**
     * 
     * @param source
     * @param destination
     * @param prevFlightMap
     * @return
     */

    private List<Flight> buildRouteFromPrevMap(String source, String destination, Map<String, Flight> prevFlightMap) {
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
    /**
     * 
     * @param route
     */

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
