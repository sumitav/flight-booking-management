package com.flightBookingSystem.utils;

public class InputValidator {
    public static boolean isValidCityCode(String city) {
        return city.length() == 3 && city.matches("[A-Z]{3}");
    }
    public static boolean isValidAirlineName(String airlineName) {
        return airlineName.length() < 3 || airlineName.contains(" ");
    }
    public static boolean isValidCost(double cost) {
        return cost <=0;
    }
}
