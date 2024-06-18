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
        if (request.getFrom() == null || request.getTo() == null ||
                request.getCarrier() == null ||
                request.getDepartureTime() == null || request.getDepartureTime().isEmpty() ||
                request.getArrivalTime() == null || request.getArrivalTime().isEmpty() ||
                request.getFrom().getCountry() == null || request.getFrom().getCountry().isEmpty() ||
                request.getFrom().getCity() == null || request.getFrom().getCity().isEmpty() ||
                request.getFrom().getAirport() == null || request.getFrom().getAirport().isEmpty() ||
                request.getTo().getCountry() == null || request.getTo().getCountry().isEmpty() ||
                request.getTo().getCity() == null || request.getTo().getCity().isEmpty() ||
                request.getTo().getAirport() == null || request.getTo().getAirport().isEmpty() ||
                request.getCarrier().isEmpty()) {
            throw new InvalidValueException("Flight data cannot contain null or empty values");
        }

        if (request.getFrom().equals(request.getTo())) {
            throw new InvalidFlightException("Departure and arrival airports cannot be the same");
        }

        if (!request.isDepartureBeforeArrival()) {
            throw new InvalidDateException("Departure time cannot be after or equal to arrival time");
        }
        return flightRepository.addFlight(request);
    }

    public void deleteFlightById(Long id) {
        flightRepository.deleteFlightById(id);
    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new InvalidFlightException("From and to airports cannot be the same");
        }
        List<Flight> flights = flightRepository.searchFlights(request);
        int totalItems = flights.size();
        return new PageResult<>(0, totalItems, flights);
    }
}

