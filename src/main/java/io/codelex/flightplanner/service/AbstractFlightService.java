package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.InvalidDateException;
import io.codelex.flightplanner.exceptions.InvalidFlightException;
import io.codelex.flightplanner.exceptions.InvalidValueException;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.SearchFlightsRequest;

abstract class AbstractFlightService implements FlightService {
    public void validateFlight(Flight flight){
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
    public void validateSearchFlightsRequest(SearchFlightsRequest request){
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
