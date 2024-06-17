package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.repository.AirportDbRepository;

import java.util.ArrayList;
import java.util.List;

public class AirportDbService implements AirportService {

    private AirportDbRepository airportDbRepository;
    private final List<Airport> airports = new ArrayList<>();

    public AirportDbService(AirportDbRepository airportDbRepository) {
        this.airportDbRepository = airportDbRepository;
    }

    @Override
    public void addAirports(List<Airport> airportsToAdd) {
        this.airportDbRepository.saveAll(airportsToAdd);

    }

    @Override
    public List<Airport> searchAirports(String search) {
        return getAirports(search, airports);
    }

    static List<Airport> getAirports(String search, List<Airport> airports) {
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
