package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.FlightNotFoundException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightDbRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class FlightDbService extends AbstractFlightService {

    private final FlightDbRepository flightDbRepository;

    public FlightDbService(FlightDbRepository flightDbRepository) {
        this.flightDbRepository = flightDbRepository;
    }

    @Override
    public void clearFlights() {
        flightDbRepository.deleteAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightDbRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
    }

    @Override
    public Flight addFlight(Flight request) {
        validateFlight(request);
        return flightDbRepository.save(request);
    }

    @Override
    public void deleteFlightById(Long id) {
        flightDbRepository.deleteById(id);
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return flightDbRepository.searchAirports(search);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        validateSearchFlightsRequest(request);
        List<Flight> flights = flightDbRepository.findAll();
        int totalItems = flights.size();
        return new PageResult<>(0, totalItems, flights);
    }
}

