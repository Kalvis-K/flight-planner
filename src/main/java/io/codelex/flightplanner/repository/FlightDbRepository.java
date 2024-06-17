package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByFromAirportAndToAirport(String fromAirport, String toAirport);
}
