package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.exceptions.*;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.AirportRepository;
import io.codelex.flightplanner.repository.FlightRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("")
@Validated
public class FlightController {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightController(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @PostMapping("/testing-api/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearFlights() {
        flightRepository.clearFlights();
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight getFlight(@Valid @PathVariable Long id) {
        Flight flight = flightRepository.getFlightById(id);
        if (flight != null) {
            return flight;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody Flight request) {
        try {
            return flightRepository.addFlight(request);
        } catch (DuplicateFlightException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        } catch (InvalidFlightException | InvalidValueException | InvalidDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid flight data");
        }
    }

    @DeleteMapping("/admin-api/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        Flight deletedFlight = flightRepository.deleteFlightById(id);
        if (deletedFlight != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return airportRepository.searchAirports(search);
    }

    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: from and to airports cannot be the same");
        }
        List<Flight> flights = flightRepository.searchFlights(request);
        int totalItems = flights.size();
        return new PageResult<>(0, totalItems, flights);
    }

    @GetMapping("/api/flights/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        Flight flight = flightRepository.getFlightById(id);
        if (flight != null) {
            return flight;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        }
    }
}