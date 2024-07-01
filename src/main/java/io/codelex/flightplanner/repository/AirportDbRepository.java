package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportDbRepository extends JpaRepository<Airport, Long> {

    List<Airport> searchAirports(String search);
}
