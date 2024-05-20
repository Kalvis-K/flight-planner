package io.codelex.flightplanner.service;

import io.codelex.flightplanner.repository.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }


}
