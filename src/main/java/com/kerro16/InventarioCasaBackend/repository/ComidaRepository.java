package com.kerro16.InventarioCasaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kerro16.InventarioCasaBackend.model.Comida;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
    boolean existsByNombre(String nombre);
}
