package com.nextstudio.mvcrest.controllers;

import com.nextstudio.mvcrest.exceptions.ResourceNotFoundException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    
    @ExceptionHandler({ConversionFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<Object>("Bad request", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object>  handleNotFoundException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>("Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
