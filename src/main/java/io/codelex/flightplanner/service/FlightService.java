package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.DuplicateFlightException;
import io.codelex.flightplanner.exceptions.InvalidDateException;
import io.codelex.flightplanner.exceptions.InvalidFlightException;
import io.codelex.flightplanner.exceptions.InvalidValueException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class FlightService {

    private final AirportService airportService;

    public FlightService(AirportService airportService) {
        this.airportService = airportService;
    }

    private final Map<Long, Flight> flightMap = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Flight addFlight(Flight request) {
        Set<Airport> airportsToAdd = new HashSet<>();
        airportsToAdd.add(request.getFrom());
        airportsToAdd.add(request.getTo());
        airportService.addAirports(new ArrayList<>(airportsToAdd));

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

        for (Flight existingFlight : flightMap.values()) {
            if (existingFlight.equals(request)) {
                throw new DuplicateFlightException("Flight already exists");
            }
        }
        Long id = nextId.getAndIncrement();
        request.setId(id);
        flightMap.put(id, request);
        return request;
    }

    public Flight getFlightById(Long id) {
        return flightMap.get(id);
    }

    public void deleteFlightById(Long id) {
        flightMap.remove(id);
    }

    public void clearFlights() {
        flightMap.clear();
        nextId.set(1);
    }

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new InvalidFlightException("From and to airports cannot be the same");
        }
        return new ArrayList<>(flightMap.values());
    }
}
