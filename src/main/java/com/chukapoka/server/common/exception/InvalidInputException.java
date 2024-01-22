package com.chukapoka.server.common.exception;

import lombok.Getter;

import java.lang.RuntimeException;

@Getter
public class InvalidInputException extends RuntimeException {
    private final String fieldName;

    public InvalidInputException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public InvalidInputException(String fieldName) {
        super("Invalid Input");
        this.fieldName = fieldName;
    }

}

