package com.example.gatling.infrastructure.exception;

public class SimulationException extends RuntimeException {
    public SimulationException(String message, Throwable cause) {
        super(message, cause);
    }
}
