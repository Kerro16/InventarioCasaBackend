package com.kerro16.InventarioCasaBackend.model;

public enum Categoria {
    LACTEOS,
    CARNES,
    VERDURAS,
    FRUTAS,
    SNACKS,
    BEBIDAS,
    CEREALES,
    CONGELADOS,
    OTROS;

    public Object equalsIgnoreCase(String categoria) {
        throw new UnsupportedOperationException("Unimplemented method 'equalsIgnoreCase'");
    }
}

