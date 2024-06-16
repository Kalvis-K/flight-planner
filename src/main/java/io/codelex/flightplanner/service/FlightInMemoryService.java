package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.InvalidFlightException;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightInMemoryService implements FlightService {

    private final FlightInMemoryRepository flightRepository;

    public FlightInMemoryService(FlightInMemoryRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new InvalidFlightException("From and to airports cannot be the same");
        }
        return flightRepository.findAllFlights();
    }
}
