package io.github.collinsigbiks.sensorops.exception;

public record ValidationError(String field, String message) {
}