package com.flightBookingSystem.entities;

import com.flightBookingSystem.interfaces.IFlight;


public class Flight implements IFlight {
    private String source;
    private String destination;
    private String airlineName;
    private double cost;
    private boolean servesMeals;

    public Flight(String source, String destination, String airlineName, double cost, boolean servesMeals) {
        this.source = source;
        this.destination = destination;
        this.airlineName = airlineName;
        this.cost = cost;
        this.servesMeals = servesMeals;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public String getAirlineName() {
        return airlineName;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean servesMeals() {
        return servesMeals;
    }

    public Double getPrice() {
        return cost;
    }
}