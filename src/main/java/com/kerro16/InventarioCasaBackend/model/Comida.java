package com.kerro16.InventarioCasaBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comida")
public class Comida {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double cantidad;
    private String fechaCaducidad;
    private String ubicacion;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private double precio;

    public Long getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Comida(Long id, String nombre, double cantidad2, String fechaCaducidad, String ubicacion,
            Categoria categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad2;
        this.fechaCaducidad = fechaCaducidad;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.precio = precio;
    }

    public Comida() {
    }
    
}
