package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.exceptions.*;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@Validated
public class FlightController {

    public final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @PostMapping("/testing-api/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearFlights() {
        flightService.clearFlights();
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight getFlight(@Valid @PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @Valid
    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody Flight request) {
        try {
            return flightService.addFlight(request);
        } catch (DuplicateFlightException e) {
            throw new DuplicateFlightException("Flight already exists");
        } catch (InvalidFlightException | InvalidValueException | InvalidDateException e) {
            throw new InvalidFlightException("Invalid flight data");
        }
    }

    @DeleteMapping("/admin-api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlightById(id);
    }

    @GetMapping("/api/airports")
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightService.searchAirports(search);
    }

    @PostMapping("/api/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        return flightService.searchFlights(request);
    }

    @GetMapping("/api/flights/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return getFlight(id);
    }
}