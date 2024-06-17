package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.exceptions.*;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.FlightRepository;
import io.codelex.flightplanner.service.AirportService;
import io.codelex.flightplanner.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class FlightController {

    private final FlightRepository flightRepository;
    private final AirportService airportService;
    private final FlightService flightService;


    public FlightController(AirportService airportService, FlightRepository flightRepository, FlightService flightService) {
        this.airportService = airportService;
        this.flightRepository = flightRepository;
        this.flightService = flightService;
    }

    @PostMapping("/testing-api/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearFlights() {
        flightRepository.clearFlights();
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight getFlight(@Valid @PathVariable Long id) {
        Flight flight = flightRepository.findFlightById(id);
        if (flight != null) {
            return flight;
        } else {
            throw new FlightNotFoundException("Flight not found");
        }
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody Flight request) {
        try {
            return flightRepository.addFlight(request);
        } catch (DuplicateFlightException e) {
            throw new DuplicateFlightException("Flight already exists");
        } catch (InvalidFlightException | InvalidValueException | InvalidDateException e) {
            throw new InvalidFlightException("Invalid flight data");
        }
    }

    @DeleteMapping("/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable Long id) {
        flightRepository.deleteFlightById(id);
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return airportService.searchAirports(search);
    }

    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        List<Flight> flights = flightService.searchFlights(request);
        int totalItems = flights.size();
        return new PageResult<>(0, totalItems, flights);
    }

    
    @GetMapping("/api/flights/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        Flight flight = flightRepository.findFlightById(id);
        if (flight != null) {
            return flight;
        } else {
            throw new FlightNotFoundException("Flight not found");
        }
    }
}