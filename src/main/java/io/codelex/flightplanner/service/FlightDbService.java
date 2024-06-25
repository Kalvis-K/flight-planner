package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.FlightNotFoundException;
import io.codelex.flightplanner.exceptions.InvalidDateException;
import io.codelex.flightplanner.exceptions.InvalidFlightException;
import io.codelex.flightplanner.exceptions.InvalidValueException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class FlightDbService extends AbstractFlightService {

    private final FlightRepository flightRepository;

    public FlightDbService(FlightRepository flightRepository) {
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

    @Override
    public void validateFlight(Flight flight) {
        if (flight.getFrom() == null || flight.getTo() == null ||
                flight.getCarrier() == null ||
                flight.getDepartureTime() == null  ||
                flight.getArrivalTime() == null  ||
                flight.getFrom().getCountry() == null || flight.getFrom().getCountry().isEmpty() ||
                flight.getFrom().getCity() == null || flight.getFrom().getCity().isEmpty() ||
                flight.getFrom().getAirport() == null || flight.getFrom().getAirport().isEmpty() ||
                flight.getTo().getCountry() == null || flight.getTo().getCountry().isEmpty() ||
                flight.getTo().getCity() == null || flight.getTo().getCity().isEmpty() ||
                flight.getTo().getAirport() == null || flight.getTo().getAirport().isEmpty() ||
                flight.getCarrier().isEmpty()) {
            throw new InvalidValueException("Flight data cannot contain null or empty values");
        }

        if (flight.getFrom().equals(flight.getTo())) {
            throw new InvalidFlightException("Departure and arrival airports cannot be the same");
        }

        if (!flight.isDepartureBeforeArrival()) {
            throw new InvalidDateException("Departure time cannot be after or equal to arrival time");
        }
    }

    @Override
    public void validateSearchFlightsRequest(SearchFlightsRequest request) {
        if (request.getFrom() == null || request.getFrom().isEmpty()) {
            throw new IllegalArgumentException("'from' airport must be specified");
        }

        if (request.getTo() == null || request.getTo().isEmpty()) {
            throw new IllegalArgumentException("'to' airport must be specified");
        }

        if (request.getDepartureDate() == null) {
            throw new IllegalArgumentException("Departure date must be specified");
        }

        if (request.getFrom().equals(request.getTo())) {
            throw new InvalidFlightException("Departure and arrival airports cannot be the same");
        }
    }
}

