package com.flightBookingSystem.utils;

public class InputValidator {
    public boolean isValidCityCode(String city) {
        return city.length() == 3 && city.matches("[A-Z]{3}");
    }
    public boolean isValidAirlineName(String airlineName) {
        return airlineName.length() < 3 || airlineName.contains(" ");
    }
    public boolean isValidCost(double cost) {
        return cost <=0;
    }
}
