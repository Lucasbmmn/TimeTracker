package com.lucasbmmn.timetracker.model.exception;

public class IllegalPhoneNumberException extends IllegalArgumentException {
    public IllegalPhoneNumberException(String message) {
        super(message);
    }
}
