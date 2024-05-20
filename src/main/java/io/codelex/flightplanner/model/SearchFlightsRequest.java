package io.codelex.flightplanner.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SearchFlightsRequest {
    @NotNull
    private String departureAirport;

    @NotNull
    private String arrivalAirport;

    @NotNull
    private LocalDate date;

    public SearchFlightsRequest(String departureAirport, String arrivalAirport, LocalDate date) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.date = date;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
