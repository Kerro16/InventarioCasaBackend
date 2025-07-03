package com.kerro16.InventarioCasaBackend.comida;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.kerro16.InventarioCasaBackend.model.Categoria;
import com.kerro16.InventarioCasaBackend.model.Comida;

class ComidaTest {

    @Test
    void testConstructorVacio() {
        Comida comida = new Comida();
        assertNotNull(comida);
        assertNull(comida.getId());
        assertNull(comida.getNombre());
        assertEquals(0.0, comida.getCantidad());
        assertNull(comida.getFechaCaducidad());
        assertNull(comida.getUbicacion());
        assertNull(comida.getCategoria());
        assertEquals(0.0, comida.getPrecio());    
    }

    @Test
    void testConstructorConParametros() {
        Long id = 1L;
        String nombre = "Pizza";
        double cantidad = 2.0;
        String fechaCaducidad = "2024-12-31";
        String ubicacion = "Nevera";
        Categoria categoria = Categoria.OTROS;
        double precio = 10.0;

        Comida comida = new Comida(id, nombre, cantidad, fechaCaducidad, ubicacion, categoria, precio);

        assertEquals(id, comida.getId());
        assertEquals(nombre, comida.getNombre());
        assertEquals(cantidad, comida.getCantidad());
        assertEquals(fechaCaducidad, comida.getFechaCaducidad());
        assertEquals(ubicacion, comida.getUbicacion());
        assertEquals(categoria, comida.getCategoria());
        assertEquals(precio, comida.getPrecio());
    }

    @Test
    void testSettersAndGetters() {
        Comida comida = new Comida();

        Long id = 1L;
        String nombre = "Pizza";
        double cantidad = 2.0;
        String fechaCaducidad = "2024-12-31";
        String ubicacion = "Nevera";
        Categoria categoria = Categoria.OTROS;
        double precio = 10.0;

        comida.setId(id);
        comida.setNombre(nombre);
        comida.setCantidad(cantidad);
        comida.setFechaCaducidad(fechaCaducidad);
        comida.setUbicacion(ubicacion);
        comida.setCategoria(categoria);
        comida.setPrecio(precio);

        assertEquals(id, comida.getId());
        assertEquals(nombre, comida.getNombre());
        assertEquals(cantidad, comida.getCantidad());
        assertEquals(fechaCaducidad, comida.getFechaCaducidad());
        assertEquals(ubicacion, comida.getUbicacion());
        assertEquals(categoria, comida.getCategoria());
        assertEquals(precio, comida.getPrecio());
    }

    @Test
    void testCategoriasEnum() {
        Comida comida = new Comida();
        
        // Probar cada valor del enum
        comida.setCategoria(Categoria.CARNES);
        assertEquals(Categoria.CARNES, comida.getCategoria());
        
        comida.setCategoria(Categoria.VERDURAS);
        assertEquals(Categoria.VERDURAS, comida.getCategoria());
        
        comida.setCategoria(Categoria.FRUTAS);
        assertEquals(Categoria.FRUTAS, comida.getCategoria());
        
        comida.setCategoria(Categoria.LACTEOS);
        assertEquals(Categoria.LACTEOS, comida.getCategoria());
        
        comida.setCategoria(Categoria.OTROS);
        assertEquals(Categoria.OTROS, comida.getCategoria());
    }
}
