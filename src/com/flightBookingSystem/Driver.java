package com.flightBookingSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Airline;
import com.flightBookingSystem.interfaces.IAirline;
import com.flightBookingSystem.services.FlightSearchService;
import com.flightBookingSystem.utils.CommandParser;
import com.flightBookingSystem.utils.InputValidator;

public class Driver {
    public static void main(String[] args) {
        FlightDAO flightDAO = FlightDAO.getInstance();
        Map<String, IAirline> airlines = new HashMap<>();
        //register flights before
        airlines.put("JetAir", new Airline("JetAir", flightDAO));
        airlines.put("Delta", new Airline("Delta", flightDAO));
        airlines.put("Indigo", new Airline("Indigo", flightDAO));
        airlines.put("AirIndia", new Airline("AirIndia", flightDAO));
        FlightSearchService flightSearchService = new FlightSearchService(flightDAO);
        InputValidator inputValidator = new InputValidator();
        //used to parse each commands
        CommandParser commandParser = new CommandParser(airlines, flightSearchService,inputValidator);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to the Flight Booking System!");
            System.out.println("Enter 'exit' to quit the application.");

            while (true) {
                System.out.print("\nEnter command: ");
                String input = scanner.nextLine().trim();
                commandParser.parseCommand(input);
            }
        }
    }
}