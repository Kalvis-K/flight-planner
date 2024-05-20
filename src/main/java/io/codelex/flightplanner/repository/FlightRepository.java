package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.exceptions.DuplicateFlightException;
import io.codelex.flightplanner.exceptions.InvalidDateException;
import io.codelex.flightplanner.exceptions.InvalidFlightException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.exceptions.InvalidValueException;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class FlightRepository {

    private AirportRepository airportRepository;

    public FlightRepository(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    private final Map<Long, Flight> flightMap = new HashMap<>();
    private Long nextId = 1L;


    public Flight addFlight(Flight request)
    {
        List<Airport> airportsToAdd = new ArrayList<>();
        airportsToAdd.add(request.getFrom());
        airportsToAdd.add(request.getTo());
        airportRepository.addAirports(airportsToAdd);

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
        request.setId(nextId++);
        flightMap.put(request.getId(), request);
        return request;
    }

    public Flight getFlightById(Long id) {
        return flightMap.get(id);
    }

    public Flight deleteFlightById(Long id) {
        return flightMap.remove(id);
    }

    public void clearFlights() {
        flightMap.clear();
        nextId = 1L;
    }

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        List<Flight> matchingFlights = new ArrayList<>();
        for (Flight flight : flightMap.values()) {
            if (flight.getFrom().getAirport().equals(request.getDepartureAirport()) &&
                    flight.getTo().getAirport().equals(request.getArrivalAirport()) &&
                    flight.getDepartureTime().equals(request.getDate())) {
                matchingFlights.add(flight);
            }
        }
        return matchingFlights;
    }
}
