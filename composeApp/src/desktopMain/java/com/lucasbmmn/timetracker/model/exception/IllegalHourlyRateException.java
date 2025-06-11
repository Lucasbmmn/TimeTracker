package com.lucasbmmn.timetracker.model.exception;

public class IllegalHourlyRateException extends IllegalArgumentException {
    public IllegalHourlyRateException(String message) {
        super(message);
    }
}
