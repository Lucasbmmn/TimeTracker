package com.lucasbmmn.timetracker.model.exception;

public class IllegalEmailException extends IllegalArgumentException {
    public IllegalEmailException(String message) {
        super(message);
    }
}
