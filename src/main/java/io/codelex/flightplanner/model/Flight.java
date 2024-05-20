package io.codelex.flightplanner.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Flight {

    @NotNull
    private Long id;

    @Valid
    @NotNull
    private Airport from;

    @Valid
    @NotNull
    private Airport to;

    @NotBlank
    private String carrier;

    @NotBlank
    private String departureTime;

    @NotBlank
    private String arrivalTime;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public @Valid @NotNull Airport getFrom() {
        return from;
    }

    public void setFrom(@Valid @NotNull Airport from) {
        this.from = from;
    }

    public @Valid @NotNull Airport getTo() {
        return to;
    }

    public void setTo(@Valid @NotNull Airport to) {
        this.to = to;
    }

    public @NotBlank String getCarrier() {
        return carrier;
    }

    public void setCarrier(@NotBlank String carrier) {
        this.carrier = carrier;
    }

    public @NotBlank String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@NotBlank String departureTime) {
        this.departureTime = departureTime;
    }

    public @NotBlank String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(@NotBlank String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getParsedDepartureTime() {
        return LocalDateTime.parse(departureTime, formatter);
    }

    public LocalDateTime getParsedArrivalTime() {
        return LocalDateTime.parse(arrivalTime, formatter);
    }

    public boolean isDepartureBeforeArrival() {
        return getParsedDepartureTime().isBefore(getParsedArrivalTime());
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(carrier, flight.carrier) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(arrivalTime, flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, carrier, departureTime, arrivalTime);
    }
}
