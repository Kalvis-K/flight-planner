package io.codelex.flightplanner.exceptions;

public class InvalidFlightException extends RuntimeException {
    public InvalidFlightException(String message) {
        super(message);
    }
}
