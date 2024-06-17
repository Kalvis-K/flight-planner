package io.codelex.flightplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

@Entity
public class Airport {

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String airport;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public Airport() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public boolean isEqualAirport(Airport otherAirport) {
        return this.equals(otherAirport);
    }

    @Override
    public String toString() {
        return "Airport {" +
                "airport:' " + airport + '\'' +
                ", city:' " + city + '\'' +
                ", country:' " + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return Objects.equals(country.toLowerCase(), airport1.country.toLowerCase()) &&
                Objects.equals(city.toLowerCase(), airport1.city.toLowerCase()) &&
                Objects.equals(airport.toUpperCase().trim(), airport1.airport.toUpperCase().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
