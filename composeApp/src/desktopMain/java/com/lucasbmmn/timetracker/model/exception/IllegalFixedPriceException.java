package com.lucasbmmn.timetracker.model.exception;

public class IllegalFixedPriceException extends IllegalArgumentException {
    public IllegalFixedPriceException(String message) {
        super(message);
    }
}
