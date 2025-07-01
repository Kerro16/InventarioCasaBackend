package com.kerro16.InventarioCasaBackend.dto;

public class ComidaDTO {
    
    private Long id;
    private String nombre;
    private String categoria;
    private int cantidad;
    private String fechaCaducidad;
    private String ubicacion;

    public ComidaDTO() {
    }
    public ComidaDTO(Long id, String nombre, String categoria, int cantidad, String fechaCaducidad, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
        this.ubicacion = ubicacion;
    }

    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public int getCantidad() {
        return cantidad;
    }
    public String getFechaCaducidad() {
        return fechaCaducidad;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    @Override
    public String toString() {
        return "ComidaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", cantidad=" + cantidad +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }

  
}
