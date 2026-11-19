package com.covoiturage.exception;


import com.covoiturage.exception.EmailAlreadyExistsException;
import com.covoiturage.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e, HttpServletRequest request) {

        return build(HttpStatus.CONFLICT,e.getMessage(),request);
    }
    @ExceptionHandler(LicenseNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleLicenseNumberAlreadyExistsException(LicenseNumberAlreadyExistsException e, HttpServletRequest request) {

        return build(HttpStatus.CONFLICT,e.getMessage(),request);
    }
    @ExceptionHandler(ConducteurNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConducteurNotFoundException(ConducteurNotFoundException e, HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND,e.getMessage(),request);
    }

    @ExceptionHandler(ImmatriculationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleImmatriculationAlreadyExistsException(ImmatriculationAlreadyExistsException e, HttpServletRequest request) {

        return build(HttpStatus.CONFLICT,e.getMessage(),request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(BusinessRuleException e, HttpServletRequest request) {

        return build(HttpStatus.CONFLICT,e.getMessage(),request);
    }

    @ExceptionHandler(VehiculeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVehiculeNotFoundException(VehiculeNotFoundException e, HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND,e.getMessage(),request);
    }



    private ResponseEntity<ErrorResponse> build(
            HttpStatus status, String message, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .error(status.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
