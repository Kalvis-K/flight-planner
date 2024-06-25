package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;

abstract class AbstractFlightService implements FlightService {
    public abstract void validateFlight(Flight flight);
    public abstract void validateSearchFlightsRequest(SearchFlightsRequest request);
}
