package com.chukapoka.server.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Stream;

public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum annotation) {
        enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Stream.of(enumValues).anyMatch(e -> e.name().equals(value));
    }
}

