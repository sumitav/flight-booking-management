package com.flightBookingSystem.services;

import com.flightBookingSystem.dao.FlightDAO;
import com.flightBookingSystem.entities.Airline;
import com.flightBookingSystem.entities.Flight;

public class airlineManagerServiceImpl implements airlineManagerService {
    private final  FlightDAO flightDAO;
    public airlineManagerServiceImpl(FlightDAO flightDAO) {
        this.flightDAO = flightDAO;
    }
    @Override
    public void registerFlight(Airline airline, String source, String destination, double cost, boolean servesMeals) {

        try {
            Flight flight = new Flight();
            flight.setSource(source);
            flight.setDestination(destination);
            flight.setAirlineName(airline.getName());
            flight.setCost(cost);
            flight.setServeMeals(servesMeals);
            flightDAO.addFlight(flight);
            System.out.println(airline.getName() + " " + source + " -> " + destination + " flight registered.");
        } catch (Exception e) {
            System.out.println("Error registering flight: " + e.getMessage());
        }
    }
}
