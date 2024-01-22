package com.chukapoka.server.common.exception;

import com.chukapoka.server.common.dto.BaseResponse;
import com.chukapoka.server.common.enums.ResultType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse<Map<String, String>>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage != null ? errorMessage : "Not Exception Message");
        });
        return new ResponseEntity<>(new BaseResponse<>(ResultType.ERROR, errors, ResultType.ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<BaseResponse<Map<String, String>>> invalidInputException(InvalidInputException ex) {
        Map<String, String> errors = Map.of(ex.getFieldName(), ex.getMessage() != null ? ex.getMessage() : "Not Exception Message");
        return new ResponseEntity<>(new BaseResponse<>(ResultType.ERROR, errors, ResultType.ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse<Map<String, String>>> defaultExceptionHandler(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Exception", ex.getMessage() != null ? ex.getMessage() : "Not Exception Message");
        return new ResponseEntity<>(new BaseResponse<>(ResultType.ERROR, errors, ResultType.ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }
}
