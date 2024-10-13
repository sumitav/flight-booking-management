package com.flightBookingSystem.entities;
import java.util.List;

import com.flightBookingSystem.interfaces.IAirline;
import com.flightBookingSystem.interfaces.IFlight;
import com.flightBookingSystem.interfaces.IFlightDAO;

public class Airline implements IAirline {
    private String name;
    private List<IFlight> flights;
    private IFlightDAO flightDAO;

    public Airline(String name, IFlightDAO flightDAO) {
        if (name == null || name.length() <= 2 || name.contains(" ")) {
            throw new IllegalArgumentException("Invalid airline name.");
        }
        this.name = name;
        this.flightDAO = flightDAO;
    }
    @Override
    public void registerFlight(String source, String destination, double cost, boolean servesMeals) {
        Flight flight = new Flight(source, destination, this.name, cost, servesMeals);
        flightDAO.addFlight(flight);
        System.out.println(this.name + " " + source + "->" + destination + " flight registered");
    }
    public List<IFlight> getFlights() {
        return flights;
    }

    public String getName() {
        return name;
    }
}
