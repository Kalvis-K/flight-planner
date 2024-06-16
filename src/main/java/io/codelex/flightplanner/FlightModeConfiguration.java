package io.codelex.flightplanner;

import io.codelex.flightplanner.repository.FlightDbRepository;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.service.FlightDbService;
import io.codelex.flightplanner.service.FlightInMemoryService;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.beans.factory.annotation.Value;

public class FlightModeConfiguration {

    @Value("${myapp.flight-storage-mode}")
    private String storageMode;

    public FlightService createFlightServiceBean(FlightInMemoryRepository flightInMemoryRepository, FlightDbRepository flightDbRepository) {
        if ("database".equals(storageMode)) {
            return new FlightDbService(flightDbRepository);
        } else {
            return new FlightInMemoryService(flightInMemoryRepository);
        }
    }
}
