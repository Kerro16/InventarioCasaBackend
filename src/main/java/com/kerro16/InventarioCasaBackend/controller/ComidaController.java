package com.kerro16.InventarioCasaBackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kerro16.InventarioCasaBackend.dto.ComidaDTO;
import com.kerro16.InventarioCasaBackend.service.ComidaService;


@RestController
@RequestMapping("/api/comida")
public class ComidaController {
    
    private final ComidaService comidaService;

    public ComidaController(ComidaService comidaService) {
        this.comidaService = comidaService;
    }


    @GetMapping("/listar")
    public ResponseEntity<List<ComidaDTO>> getComida(){
        List<ComidaDTO> comidas = comidaService.listarTodas();
        System.out.println("Comidas: " + comidas);
        if (comidas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComidaDTO> getComidaById(@PathVariable Long id) {
        ComidaDTO comida = comidaService.buscarPorId(id);
        if (comida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comida);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ComidaDTO>> getComidaByNombre(@PathVariable String nombre) {
        List<ComidaDTO> comidas = comidaService.buscarPorNombre(nombre);
        if (comidas == null || comidas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ComidaDTO>> getComidaByCategoria(@PathVariable String categoria) {
        List<ComidaDTO> comidas = comidaService.buscarPorCategoria(categoria);
        if (comidas == null || comidas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comidas);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarComida(@PathVariable Long id, @RequestBody ComidaDTO comidaDTO) {
        ComidaDTO comidaActualizada = comidaService.actualizar(id, comidaDTO);
        if (comidaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Comida actualizada correctamente");
    }

    @PostMapping("/guardar")
    public ResponseEntity<String> guardarComida(@RequestBody ComidaDTO comidaDTO) {
        ComidaDTO comidaGuardada = comidaService.guardar(comidaDTO);
        if (comidaGuardada == null) {
            return ResponseEntity.badRequest().body("Error al guardar la comida");
        }
        return ResponseEntity.ok("Comida guardada correctamente");

    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarComida(@PathVariable Long id) {
        try {
            comidaService.eliminar(id);
            return ResponseEntity.ok("Comida eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/autocompletar")
    public ResponseEntity<List<String>> autocompletarNombre(@RequestParam(required = false) String q) {
        List<String> sugerencias = comidaService.autocompletar(q);
        if (sugerencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sugerencias);
    }

}
