package com.gestaodebibliotecas.gestaodebibliotecas.handler;

import com.gestaodebibliotecas.gestaodebibliotecas.exception.BusinessException;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException (ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException (BusinessException ex) {
        return ResponseEntity.status(422).body(ex.getMessage());
    }
}
