package com.lucasbmmn.timetracker.model.exception;

public class IllegalTimezoneException extends IllegalArgumentException {
    public IllegalTimezoneException(String message) {
        super(message);
    }
}
