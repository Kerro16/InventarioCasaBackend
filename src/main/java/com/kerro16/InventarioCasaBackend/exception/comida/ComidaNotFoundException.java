package com.kerro16.InventarioCasaBackend.exception.comida;

public class ComidaNotFoundException extends ComidaException {
    public ComidaNotFoundException(Long id) {
        super(id != null
        ? "No se encontró comida con ID:" + id
                        :"No se encontró comida en el sistema"
        );
    }
}
