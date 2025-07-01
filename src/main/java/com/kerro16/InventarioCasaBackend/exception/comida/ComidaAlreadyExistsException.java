package com.kerro16.InventarioCasaBackend.exception.comida;

public class ComidaAlreadyExistsException extends ComidaException {
    public ComidaAlreadyExistsException(String nombre) {
        super("Ya existe una comida con el nombre: " + nombre);
    }  
}
