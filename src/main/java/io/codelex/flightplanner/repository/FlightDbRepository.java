package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {

}