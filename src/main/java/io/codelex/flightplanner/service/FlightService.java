package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FlightService {
    void clearFlights();

    Flight getFlightById(Long id);

    Flight addFlight(Flight request);

    void deleteFlightById(Long id);

    List<Airport> searchAirports(String search);

    PageResult<Flight> searchFlights(SearchFlightsRequest request);
}
