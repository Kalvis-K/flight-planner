package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportDbRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT a FROM Airport a WHERE a.country = :country AND a.city = :city AND a.airport = :airport")
    Optional<Airport> findByCountryAndCityAndAirport(String country, String city, String airport);

    @Query("SELECT a FROM Airport a " +
            "WHERE LOWER(TRIM(a.country)) LIKE LOWER(CONCAT('%', TRIM(:search), '%')) " +
            "OR LOWER(TRIM(a.city)) LIKE LOWER(CONCAT('%', TRIM(:search), '%')) " +
            "OR LOWER(TRIM(a.airport)) LIKE LOWER(CONCAT('%', TRIM(:search), '%'))")
    List<Airport> searchAirports(@Param("search") String search);
}
