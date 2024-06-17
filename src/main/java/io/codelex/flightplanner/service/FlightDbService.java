package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightDbRepository;

import java.util.List;

public class FlightDbService implements FlightService {

    private final FlightDbRepository flightDbRepository;

    public FlightDbService(FlightDbRepository flightDbRepository) {
        this.flightDbRepository = flightDbRepository;
    }

    @Override
    public List<Flight> searchFlights(SearchFlightsRequest request) {
        return flightDbRepository.findAll();
    }
}
