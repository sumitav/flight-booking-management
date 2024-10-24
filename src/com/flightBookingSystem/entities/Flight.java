package com.flightBookingSystem.entities;
public class Flight {
    private String source;
    private String destination;
    private String airlineName;
    private double cost;
    private boolean servesMeals;
    public void setSource(String source){
        this.source = source;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setAirlineName(String airlineName){
        this.airlineName = airlineName;
    }
    public void setCost(double cost){
        this.cost = cost;
    }
    public void setServeMeals(boolean servesMeals){
        this.servesMeals = servesMeals;
    }
    public String getSource() {
        return source;
    }
    public String getDestination() {
        return destination;
    }
    public String getAirlineName() {
        return airlineName;
    }
    public double getCost() {
        return cost;
    }
    public boolean servesMeals() {
        return servesMeals;
    }
}