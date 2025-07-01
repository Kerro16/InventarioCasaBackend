package com.kerro16.InventarioCasaBackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kerro16.InventarioCasaBackend.exception.comida.ComidaAlreadyExistsException;
import com.kerro16.InventarioCasaBackend.exception.comida.ComidaException;
import com.kerro16.InventarioCasaBackend.exception.comida.ComidaNotFoundException;

import jakarta.persistence.PersistenceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error interno. Por favor, intente más tarde.");
    }

    @ExceptionHandler(ComidaException.class)
    public ResponseEntity<String> handleComidaException(ComidaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La solicitud no pudo ser procesada. Por favor, verifique los datos ingresados.");
    }

    @ExceptionHandler(ComidaAlreadyExistsException.class)
    public ResponseEntity<String> handleComidaAlreadyExistsException(ComidaAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El recurso que intenta crear ya existe en el sistema.");
    }

    @ExceptionHandler(ComidaNotFoundException.class)
    public ResponseEntity<String> handleComidaNotFoundException(ComidaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El recurso solicitado no fue encontrado.");
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handlePersistenceException(PersistenceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el procesamiento de datos. Por favor, intente más tarde.");
    }

}
