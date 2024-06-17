package io.codelex.flightplanner.service;

import io.codelex.flightplanner.model.Airport;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AirportService {
    public void addAirports(List<Airport> airportsToAdd);
    public List<Airport> searchAirports(@RequestParam String search);
}
