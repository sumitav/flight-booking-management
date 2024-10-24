package com.flightBookingSystem.utils;

import java.util.Map;

import com.flightBookingSystem.constants.constants;
import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Airline;
import com.flightBookingSystem.services.airlineManagerServiceImpl;
import com.flightBookingSystem.services.flightSearchServiceImpl;
import com.flightBookingSystem.strategy.CheapestFlightSearchStrategy;
import com.flightBookingSystem.strategy.MinHopsFlightSearchStrategy;

public class CommandParser {
    private Map<String, Airline> airlines;
    private flightSearchServiceImpl flightSearchService;
    private airlineManagerServiceImpl airlineManager;
    public CommandParser(Map<String, Airline> airlines, flightSearchServiceImpl flightSearchService,airlineManagerServiceImpl airlineManager) {
        this.airlines = airlines;
        this.flightSearchService = flightSearchService;
        this.airlineManager=airlineManager;
    }

    public void parseCommand(String input) {
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Exiting the system.");
            System.exit(0);
        } else if (input.startsWith("register flight")) {
            parseRegisterFlight(input);
        } else if (input.startsWith("search flight")) {
            parseSearchFlight(input);
        } else {
            System.out.println("Invalid command. Try again.");
        }
    }

    private void parseRegisterFlight(String input) {
        // Expected format: register flight->Airline->Source->Destination->Cost
        String[] parts = input.split("->");
        if (parts.length != 5) {
            System.out.println("Invalid command format. Correct format: register flight->Airline->Source->Destination->Cost");
            return;
        }

        String airlineName = parts[1].trim();
        String source = parts[2].trim();
        String destination = parts[3].trim();
        double cost;
        if (InputValidator.isValidAirlineName(airlineName)) {
            System.out.println("Invalid airline name. Airline names must have no spaces and be at least 3 characters long.");
            return;
        }
        if (!InputValidator.isValidCityCode(source)) {
            System.out.println("Invalid source city code. Must be a 3-letter code.");
            return;
        }
        if (!InputValidator.isValidCityCode(destination)) {
            System.out.println("Invalid destination city code. Must be a 3-letter code.");
            return;
        }

        try {
            cost = Double.parseDouble(parts[4].trim());
            if (InputValidator.isValidCost(cost)) {
                System.out.println("Invalid cost value. Cost must be a positive number greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid cost value.");
            return;
        }

        Airline airline = airlines.get(airlineName);
        if (airline == null) {
            System.out.println("Airline " + airlineName + " not found.");
            return;
        }

        boolean servesMeals = airlineName.equalsIgnoreCase(constants.MEAL_SERVING_AIRLINE);
        airlineManager.registerFlight(airline, source, destination, cost, servesMeals);
    }

    private void parseSearchFlight(String input) {
        // Expected format: search flight->Source->Destination->[TRUE/FALSE]
        String[] parts = input.split("->");
        if (parts.length < 3 || parts.length > 4) {
            System.out.println("Invalid command format. Correct format: search flight->Source->Destination->[TRUE/FALSE]");
            return;
        }

        String source = parts[1].trim();
        String destination = parts[2].trim();
        boolean requiresMeals = false;

        if (parts.length == 4) {
            requiresMeals = Boolean.parseBoolean(parts[3].trim());
        }
        flightSearchService.setSearchStrategy(new MinHopsFlightSearchStrategy(FlightDAO.getInstance()));
        System.out.println("Route with minimum hops:");
        flightSearchService.searchFlights(source, destination, requiresMeals);
        flightSearchService.setSearchStrategy(new CheapestFlightSearchStrategy(FlightDAO.getInstance()));
        System.out.println("\nCheapest Route:");
        flightSearchService.searchFlights(source, destination, requiresMeals);
    }
}
