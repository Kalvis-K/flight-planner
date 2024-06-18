package io.codelex.flightplanner.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SearchFlightsRequest {
    @NotNull
    private String from;

    @NotNull
    private String to;

    @NotNull
    private LocalDate departureDate;

    public SearchFlightsRequest(String from, String to, LocalDate departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public @NotNull String getFrom() {
        return from;
    }

    public void setFrom(@NotNull String from) {
        this.from = from;
    }

    public @NotNull String getTo() {
        return to;
    }

    public void setTo(@NotNull String to) {
        this.to = to;
    }

    public @NotNull LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(@NotNull LocalDate departureDate) {
        this.departureDate = departureDate;
    }
}
