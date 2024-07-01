package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.repository.AirportDbRepository;
import io.codelex.flightplanner.repository.FlightDbRepository;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.service.FlightDbService;
import io.codelex.flightplanner.service.FlightInMemoryService;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Value("${myapp.flight-storage-mode}")
    private String storageMode;

    @Bean
    public FlightService createFlightServiceBean(FlightInMemoryRepository flightInMemoryRepository, FlightDbRepository flightDbRepository, AirportDbRepository airportDbRepository){

        if ("database".equals(storageMode)) {
            return new FlightDbService(flightDbRepository, airportDbRepository);
        } else {
            return new FlightInMemoryService(flightInMemoryRepository);
        }
    }
}
