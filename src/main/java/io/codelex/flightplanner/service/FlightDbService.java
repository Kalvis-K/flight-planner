package io.codelex.flightplanner.service;

import io.codelex.flightplanner.exceptions.DuplicateFlightException;
import io.codelex.flightplanner.exceptions.FlightNotFoundException;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import io.codelex.flightplanner.model.PageResult;
import io.codelex.flightplanner.model.SearchFlightsRequest;
import io.codelex.flightplanner.repository.AirportDbRepository;
import io.codelex.flightplanner.repository.FlightDbRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("db")
public class FlightDbService extends AbstractFlightService {

    private final FlightDbRepository flightDbRepository;
    private final AirportDbRepository airportDbRepository;

    public FlightDbService(FlightDbRepository flightDbRepository, AirportDbRepository airportDbRepository) {
        this.flightDbRepository = flightDbRepository;
        this.airportDbRepository = airportDbRepository;
    }

    @Override
    public void clearFlights() {
        flightDbRepository.deleteAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightDbRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
    }

    @Override
    public Flight addFlight(Flight request) {
        validateFlight(request);

        Airport fromAirport = fetchOrCreateAirport(request.getFrom());

        Airport toAirport = fetchOrCreateAirport(request.getTo());

        Optional<Flight> duplicateFlight = flightDbRepository.findDuplicateFlight(fromAirport, toAirport, request.getDepartureTime(), request.getCarrier());
        if (duplicateFlight.isPresent()) {
            throw new DuplicateFlightException("Flight already exists");
        }

        request.setFrom(fromAirport);
        request.setTo(toAirport);

        return flightDbRepository.save(request);
    }

    @Override
    public void deleteFlightById(Long id) {
        flightDbRepository.deleteById(id);
    }

    @Override
    public List<Airport> searchAirports(String search) {
        return airportDbRepository.searchAirports(search);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightsRequest request) {
        validateSearchFlightsRequest(request);
        List<Flight> flights = flightDbRepository.findAll();
        int totalItems = flights.size();
        return new PageResult<>(0, totalItems, flights);
    }

    private Airport fetchOrCreateAirport(Airport airport) {
        Optional<Airport> existingAirport = airportDbRepository.findByCountryAndCityAndAirport(
                airport.getCountry(), airport.getCity(), airport.getAirport());

        return existingAirport.orElseGet(() -> airportDbRepository.save(airport));
    }
}

