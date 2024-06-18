package io.codelex.flightplanner.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FlightExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateFlightException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(DuplicateFlightException ex, WebRequest request) {
        String bodyOfResponse = "Error: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InvalidFlightException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(InvalidFlightException ex, WebRequest request) {
        String bodyOfResponse = "Error: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(FlightNotFoundException ex, WebRequest request) {
        String bodyOfResponse = "Error: " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
