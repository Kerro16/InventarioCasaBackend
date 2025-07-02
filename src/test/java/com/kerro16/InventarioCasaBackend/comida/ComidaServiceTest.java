package com.kerro16.InventarioCasaBackend.comida;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.kerro16.InventarioCasaBackend.dto.ComidaDTO;
import com.kerro16.InventarioCasaBackend.model.Categoria;
import com.kerro16.InventarioCasaBackend.model.Comida;
import com.kerro16.InventarioCasaBackend.repository.ComidaRepository;
import com.kerro16.InventarioCasaBackend.service.ComidaService;
import com.kerro16.InventarioCasaBackend.exception.comida.ComidaNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ComidaServiceTest {

    @Mock
    private ComidaRepository comidaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ComidaService comidaService;

    @Test
    void testGuardarComida() {
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setNombre("Pizza");
        comidaDTO.setCategoria("OTROS");
        comidaDTO.setCantidad(2.0);
        comidaDTO.setFechaCaducidad("2023-12-31");
        comidaDTO.setUbicacion("Nevera");


        Comida comida = new Comida();
        comida.setNombre("Pizza");
        comida.setCategoria(Categoria.OTROS);
        comida.setCantidad(2.0);
        comida.setFechaCaducidad("2023-12-31");
        comida.setUbicacion("Nevera");

        when(modelMapper.map(comidaDTO, Comida.class)).thenReturn(comida);
        when(comidaRepository.save(comida)).thenReturn(comida);
        when(modelMapper.map(comida, ComidaDTO.class)).thenReturn(comidaDTO);

        ComidaDTO resultado = comidaService.guardar(comidaDTO);

        assertNotNull(resultado);
        assertEquals("Pizza", resultado.getNombre());
        assertEquals("OTROS", resultado.getCategoria());
        assertEquals(2.0, resultado.getCantidad());
        assertEquals("2023-12-31", resultado.getFechaCaducidad());
        assertEquals("Nevera", resultado.getUbicacion());

    
    }
    @Test
    void testBuscarPorId() {
        // Preparar datos
        Long id = 1L;
        Comida comida = new Comida();
        comida.setId(id);
        comida.setNombre("Pizza");
        comida.setCategoria(Categoria.OTROS);

        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(id);
        comidaDTO.setNombre("Pizza");
        comidaDTO.setCategoria("OTROS");

        // Configurar comportamiento mock
        when(comidaRepository.findById(id)).thenReturn(Optional.of(comida));
        when(modelMapper.map(comida, ComidaDTO.class)).thenReturn(comidaDTO);

        // Ejecutar
        ComidaDTO resultado = comidaService.buscarPorId(id);

        // Verificar
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Pizza", resultado.getNombre());
        assertEquals("OTROS", resultado.getCategoria());
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // Preparar
        Long id = 1L;
        when(comidaRepository.findById(id)).thenReturn(Optional.empty());

        // Verificar que se lance la excepción
        assertThrows(ComidaNotFoundException.class, () -> comidaService.buscarPorId(id));
    }

    @Test
    void testActualizar() {
        // Preparar datos
        Long id = 1L;
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setNombre("Pizza Actualizada");
        comidaDTO.setCategoria("OTROS");
        comidaDTO.setCantidad(3.0);
        comidaDTO.setFechaCaducidad("2024-12-31");
        comidaDTO.setUbicacion("Congelador");

        Comida comidaExistente = new Comida();
        comidaExistente.setId(id);
        comidaExistente.setNombre("Pizza");
        comidaExistente.setCategoria(Categoria.OTROS);
        comidaExistente.setCantidad(2.0);
        comidaExistente.setFechaCaducidad("2023-12-31");
        comidaExistente.setUbicacion("Nevera");

        // Simular comportamiento de modelMapper.map actualizando el objeto existente
        doAnswer(invocation -> {
            Comida comidaTarget = invocation.getArgument(1);
            comidaTarget.setNombre(comidaDTO.getNombre());
            comidaTarget.setCategoria(Categoria.valueOf(comidaDTO.getCategoria()));
            comidaTarget.setCantidad(comidaDTO.getCantidad());
            comidaTarget.setFechaCaducidad(comidaDTO.getFechaCaducidad());
            comidaTarget.setUbicacion(comidaDTO.getUbicacion());
            return null;
        }).when(modelMapper).map(eq(comidaDTO), any(Comida.class));

        // Configurar mocks
        when(comidaRepository.findById(id)).thenReturn(Optional.of(comidaExistente));
        when(comidaRepository.save(any(Comida.class))).thenAnswer(i -> i.getArgument(0));
        when(modelMapper.map(any(Comida.class), eq(ComidaDTO.class))).thenReturn(comidaDTO);

        // Ejecutar
        ComidaDTO resultado = comidaService.actualizar(id, comidaDTO);

        // Verificar
        assertNotNull(resultado);
        assertEquals("Pizza Actualizada", resultado.getNombre());
        assertEquals("OTROS", resultado.getCategoria());
        assertEquals(3.0, resultado.getCantidad());
        assertEquals("2024-12-31", resultado.getFechaCaducidad());
        assertEquals("Congelador", resultado.getUbicacion());
        
        // Verificar que se llamó a los métodos correctos
        verify(comidaRepository).findById(id);
        verify(comidaRepository).save(any(Comida.class));
    }

    @Test
    void testEliminar() {
        // Preparar
        Long id = 1L;
        when(comidaRepository.existsById(id)).thenReturn(true);

        // Ejecutar
        assertDoesNotThrow(() -> comidaService.eliminar(id));

        // Verificar
        verify(comidaRepository).deleteById(id);
    }

    @Test
    void testEliminarNoEncontrado() {
        // Preparar
        Long id = 1L;
        when(comidaRepository.existsById(id)).thenReturn(false);

        // Verificar que se lance la excepción
        assertThrows(ComidaNotFoundException.class, () -> comidaService.eliminar(id));
    }

    @Test
    void testListarTodas() {
        // Preparar datos
        List<Comida> comidas = Arrays.asList(
            new Comida(), new Comida()
        );
        List<ComidaDTO> comidasDTO = Arrays.asList(
            new ComidaDTO(), new ComidaDTO()
        );

        // Configurar mocks
        when(comidaRepository.findAll()).thenReturn(comidas);
        when(modelMapper.map(any(Comida.class), eq(ComidaDTO.class)))
            .thenReturn(comidasDTO.get(0), comidasDTO.get(1));

        // Ejecutar
        List<ComidaDTO> resultado = comidaService.listarTodas();

        // Verificar
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void testBuscarPorNombre() {
        // Preparar datos
        String nombre = "Pizza";
        Comida comida = new Comida();
        comida.setNombre(nombre);
        List<Comida> comidas = Arrays.asList(comida);

        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setNombre(nombre);

        // Configurar mocks
        when(comidaRepository.findAll()).thenReturn(comidas);
        when(modelMapper.map(comida, ComidaDTO.class)).thenReturn(comidaDTO);

        // Ejecutar
        List<ComidaDTO> resultado = comidaService.buscarPorNombre(nombre);

        // Verificar
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(nombre, resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorCategoria() {
        // Preparar datos
        String categoria = "OTROS";
        Comida comida = new Comida();
        comida.setCategoria(Categoria.OTROS);
        List<Comida> comidas = Arrays.asList(comida);

        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setCategoria(categoria);

        // Configurar mocks
        when(comidaRepository.findAll()).thenReturn(comidas);
        when(modelMapper.map(comida, ComidaDTO.class)).thenReturn(comidaDTO);

        // Ejecutar
        List<ComidaDTO> resultado = comidaService.buscarPorCategoria(categoria);

        // Verificar
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(categoria, resultado.get(0).getCategoria());
    }
}
