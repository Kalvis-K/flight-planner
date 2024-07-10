package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FlightDbRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.from = :from AND f.to = :to AND f.departureTime = :departureTime AND f.carrier = :carrier")
    Optional<Flight> findDuplicateFlight(Airport from, Airport to, LocalDateTime departureTime, String carrier);
}