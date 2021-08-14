package com.stackroute.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.msg1}")
    private String msg1;

    @Value(value = "${data.exception.msg2}")
    private String msg2;

    @ExceptionHandler(value = BlogNotFoundException.class)
    public ResponseEntity<String> blogNotFound(BlogNotFoundException blogNotFoundException){
        return new ResponseEntity<>(msg1 , HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = BlogAlreadyExistsException.class)
    public ResponseEntity<String> blogAlreadyExists(BlogAlreadyExistsException blogAlreadyExistsException){
        return new ResponseEntity<>(msg2 , HttpStatus.CONFLICT);
    }
}
