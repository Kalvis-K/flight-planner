package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.*;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("inmemory")
public class FlightInMemoryService extends AbstractFlightService {

    private final FlightRepository flightRepository;

    public FlightInMemoryService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void clearFlights() {
        flightRepository.clearFlights();
    }

    @Override
    public Flight getFlightById(Long id) {
        Flight flight = flightRepository.getFlightById(id);
        if (flight == null) {
            throw new FlightNotFoundException("Flight not found");
        }
        return flight;
    }

    @Override
    public Flight addFlight(Flight request) {
        validateFlight(request);
        return flightRepository.addFlight(request);
    }

    @Override
    public void deleteFlightById(Long id) {
        flightRepository.deleteFlightById(id);
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        validateSearchFlightsRequest(request);
        List<Flight> flights = flightRepository.searchFlights(request);
        int totalItems = flights.size();

        return new PageResult<>(0, totalItems, flights);
    }
}

