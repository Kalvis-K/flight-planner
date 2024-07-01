package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightDbRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT a FROM Airport a WHERE LOWER(a.country) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.city) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.airport) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Airport> searchAirports(String search);
}