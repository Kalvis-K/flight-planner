package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirportService {

    private final List<Airport> airports = new ArrayList<>();

    public void addAirports(List<Airport> airportsToAdd) {
        airports.addAll(airportsToAdd);
    }

    public List<Airport> searchAirports(@RequestParam String search) {
        List<Airport> matchingAirports = new ArrayList<>();
        String lowercaseSearch = search.toLowerCase().trim();
        for (Airport airport : airports) {

            if (airport.getAirport().toLowerCase().contains(lowercaseSearch) ||
                    airport.getCity().toLowerCase().contains(lowercaseSearch) ||
                    airport.getCountry().toLowerCase().contains(lowercaseSearch)) {
                matchingAirports.add(airport);
            }
        }
        return matchingAirports;
    }
}
