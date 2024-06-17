package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FlightService {
    public List<Flight> searchFlights(@RequestParam SearchFlightsRequest request);
}
