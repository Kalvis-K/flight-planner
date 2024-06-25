package io.codelex.flightplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = LocalDateTime.parse(departureTime, formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);
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

    public @NotNull LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@NotNull LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public @NotNull LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(@NotNull LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isDepartureBeforeArrival() {
        return departureTime.isBefore(arrivalTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime.format(formatter) + '\'' +
                ", arrivalTime='" + arrivalTime.format(formatter) + '\'' +
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
