package com.kerro16.InventarioCasaBackend.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kerro16.InventarioCasaBackend.model.Comida;
import com.kerro16.InventarioCasaBackend.repository.ComidaRepository;
import com.kerro16.InventarioCasaBackend.dto.ComidaDTO;
import com.kerro16.InventarioCasaBackend.exception.comida.ComidaNotFoundException;

@Service
public class ComidaService {

    private final ComidaRepository comidaRepository;
    private final ModelMapper modelMapper;

    public ComidaService(ComidaRepository comidaRepository, ModelMapper modelMapper) {
        this.comidaRepository = comidaRepository;
        this.modelMapper = modelMapper;
    }

    public ComidaDTO guardar(ComidaDTO comidaDTO) {

        Comida comida = modelMapper.map(comidaDTO, Comida.class);
        if (comidaRepository.existsByNombre(comida.getNombre())) {
            throw new com.kerro16.InventarioCasaBackend.exception.comida.ComidaAlreadyExistsException(comida.getNombre());
        }
        if (comida.getCantidad() <= 0) {
            throw new com.kerro16.InventarioCasaBackend.exception.comida.ComidaException("La cantidad debe ser mayor a cero");
        }

        System.out.println("Guardando comida: " + comida);
        Comida comidaGuardada = comidaRepository.save(comida);
        return modelMapper.map(comidaGuardada, ComidaDTO.class);
    }

    public ComidaDTO buscarPorId(Long id) {
        Comida comida = comidaRepository.findById(id)
                .orElseThrow(() -> new ComidaNotFoundException(id));
        return modelMapper.map(comida, ComidaDTO.class);
    }

    public ComidaDTO actualizar(Long id, ComidaDTO comidaDTO) {
        Comida comida = comidaRepository.findById(id)
                .orElseThrow(() -> new ComidaNotFoundException(id));
        modelMapper.map(comidaDTO, comida);
        Comida comidaActualizada = comidaRepository.save(comida);
        return modelMapper.map(comidaActualizada, ComidaDTO.class);
    }

    public void eliminar(Long id) {
        if (!comidaRepository.existsById(id)) {
            throw new ComidaNotFoundException(id);
        }
        comidaRepository.deleteById(id);
    }

    public List<ComidaDTO> listarTodas() {
        List<Comida> comidas = comidaRepository.findAll();
        return comidas.stream()
                .map(comida -> modelMapper.map(comida, ComidaDTO.class))
                .collect(Collectors.toList());
    }

    public List<ComidaDTO> buscarPorNombre(String nombre) {
        List<Comida> comidas = comidaRepository.findAll().stream()
                .filter(comida -> comida.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
        if (comidas.isEmpty()) {
            throw new ComidaNotFoundException(null);
        }
        return comidas.stream()
                .map(comida -> modelMapper.map(comida, ComidaDTO.class))
                .collect(Collectors.toList());
    }

    public List<ComidaDTO> buscarPorCategoria(String categoria) {
        List<Comida> comidas = comidaRepository.findAll().stream()
                .filter(comida -> comida.getCategoria() != null && comida.getCategoria().toString().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
        if (comidas.isEmpty()) {
            throw new ComidaNotFoundException(null);
        }
        return comidas.stream()
                .map(comida -> modelMapper.map(comida, ComidaDTO.class))
                .collect(Collectors.toList());
    }

    public List<String> autocompletar(String prefijo) {
        if (prefijo == null || prefijo.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        return comidaRepository.findAll().stream()
                .map(Comida::getNombre)
                .filter(nombre -> nombre.toLowerCase().startsWith(prefijo.toLowerCase()))
                .distinct()
                .sorted()
                .limit(10)  // Limitar a 10 sugerencias
                .collect(Collectors.toList());
    }

}
