package com.siva.bank_api;

import javax.swing.text.html.parser.Entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import  org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;;

@ControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler (RuntimeException.class)
        public ResponseEntity<String> handleRuntimeException(
            RuntimeException ex){
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
            }
    }

