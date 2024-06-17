package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.*;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }

    public Flight getFlightById(Long id) {
        Flight flight = flightRepository.getFlightById(id);
        if (flight == null) {
            throw new FlightNotFoundException("Flight not found");
        }
        return flight;
    }

    public Flight addFlight(Flight request) {
        return flightRepository.addFlight(request);
    }

    public void deleteFlightById(Long id) {
        flightRepository.deleteFlightById(id);
    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        List<Flight> flights = flightRepository.searchFlights(request);
        int totalItems = flights.size();
        return new PageResult<>(0, totalItems, flights);
    }
}

