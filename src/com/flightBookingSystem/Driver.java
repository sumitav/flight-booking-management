package com.flightBookingSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Airline;
import com.flightBookingSystem.services.airlineManagerServiceImpl;
import com.flightBookingSystem.services.flightSearchServiceImpl;
import com.flightBookingSystem.utils.CommandParser;

public class Driver {
    public static void main(String[] args) {
        FlightDAO flightDAO = FlightDAO.getInstance();
        airlineManagerServiceImpl airlineManager = new airlineManagerServiceImpl(flightDAO);
        flightSearchServiceImpl flightSearchService = new flightSearchServiceImpl();
        Map<String, Airline> airlines = new HashMap<>();
        //register flights before
        airlines.put("JetAir", new Airline("JetAir"));
        airlines.put("Delta", new Airline("Delta"));
        airlines.put("IndiGo", new Airline("IndiGo"));
        airlines.put("AirIndia", new Airline("AirIndia"));
        //used to parse each commands
        CommandParser commandParser = new CommandParser(airlines, flightSearchService,airlineManager);

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