package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static io.codelex.flightplanner.service.AirportDbService.getAirports;

@Service
public class AirportInMemoryService implements AirportService {

    private final List<Airport> airports = new ArrayList<>();

    @Override
    public void addAirports(List<Airport> airportsToAdd) {
        airports.addAll(airportsToAdd);
    }

    @Override
    public List<Airport> searchAirports(@RequestParam String search) {
        return getAirports(search, airports);
    }
}
