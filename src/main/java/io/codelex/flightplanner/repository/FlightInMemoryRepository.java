package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.exceptions.DuplicateFlightException;
import io.codelex.flightplanner.exceptions.InvalidDateException;
import io.codelex.flightplanner.exceptions.InvalidFlightException;
import io.codelex.flightplanner.exceptions.InvalidValueException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.service.AirportInMemoryService;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FlightInMemoryRepository {

    private final AirportInMemoryService airportService;

    private final Map<Long, Flight> flightMap = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public FlightInMemoryRepository(AirportInMemoryService airportService) {
        this.airportService = airportService;
    }

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

    public Flight findFlightById(Long id) {
        return flightMap.get(id);
    }

    public void deleteFlightById(Long id) {
        flightMap.remove(id);
    }

    public List<Flight> findAllFlights() {
        return List.copyOf(flightMap.values());
    }

    public void clearFlights() {
        flightMap.clear();
        nextId.set(1);
    }
}
