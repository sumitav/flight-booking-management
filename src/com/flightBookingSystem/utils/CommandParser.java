package com.flightBookingSystem.utils;

import java.util.Map;

import com.flightBookingSystem.interfaces.IAirline;
import com.flightBookingSystem.services.FlightSearchService;

public class CommandParser {
    private Map<String, IAirline> airlines;
    private FlightSearchService flightSearchService;
    private InputValidator inputValidator;
    public CommandParser(Map<String, IAirline> airlines, FlightSearchService flightSearchService,InputValidator inputValidator) {
        this.airlines = airlines;
        this.flightSearchService = flightSearchService;
        this.inputValidator = inputValidator;
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
        if (inputValidator.isValidAirlineName(airlineName)) {
            System.out.println("Invalid airline name. Airline names must have no spaces and be at least 3 characters long.");
            return;
        }
        if (!inputValidator.isValidCityCode(source)) {
            System.out.println("Invalid source city code. Must be a 3-letter code.");
            return;
        }
        if (!inputValidator.isValidCityCode(destination)) {
            System.out.println("Invalid destination city code. Must be a 3-letter code.");
            return;
        }

        try {
            cost = Double.parseDouble(parts[4].trim());
            if (inputValidator.isValidCost(cost)) {
                System.out.println("Invalid cost value. Cost must be a positive number greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid cost value.");
            return;
        }

        IAirline airline = airlines.get(airlineName);
        if (airline == null) {
            System.out.println("Airline " + airlineName + " not found.");
            return;
        }

        boolean servesMeals = airlineName.equalsIgnoreCase("Indigo"); // Assuming Indigo serves meals
        airline.registerFlight(source, destination, cost, servesMeals);
    }

    private void parseSearchFlight(String input) {
        // Expected format: search flight->Source->Destination->[TRUE/FALSE]
        String[] parts = input.split("->");
        if (parts.length < 3 || parts.length > 4) {
            System.out.println("Invalid command format. Correct format: search flight->Source->Destination->[TRUE/FALSE]");
            return;
        }

        String source = parts[1];
        String destination = parts[2];
        boolean requiresMeals = false;

        if (parts.length == 4) {
            requiresMeals = Boolean.parseBoolean(parts[3]);
        }

        flightSearchService.searchFlights(source, destination, requiresMeals);
    }
}
