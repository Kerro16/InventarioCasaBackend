package com.kerro16.InventarioCasaBackend.comida;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerro16.InventarioCasaBackend.InventarioCasaBackendApplication;
import com.kerro16.InventarioCasaBackend.config.ControllerTestConfig;
import com.kerro16.InventarioCasaBackend.dto.ComidaDTO;
import com.kerro16.InventarioCasaBackend.exception.comida.ComidaNotFoundException;
import com.kerro16.InventarioCasaBackend.service.ComidaService;

@SpringBootTest(
    classes = {InventarioCasaBackendApplication.class, ControllerTestConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ComidaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComidaService comidaService;

    @Autowired
    private ObjectMapper objectMapper;

    private ComidaDTO comidaDTO;

    @BeforeEach
    void setUp() {
        comidaDTO = new ComidaDTO();
        comidaDTO.setId(1L);
        comidaDTO.setNombre("Pizza");
        comidaDTO.setCategoria("OTROS");
        comidaDTO.setCantidad(2);
        comidaDTO.setFechaCaducidad("2024-12-31");
        comidaDTO.setUbicacion("Nevera");
    }

    @Test
    void testListarComidas() throws Exception {
        when(comidaService.listarTodas()).thenReturn(Arrays.asList(comidaDTO));

        mockMvc.perform(get("/api/comida/listar"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Pizza"))
                .andExpect(jsonPath("$[0].categoria").value("OTROS"));
    }

    @Test
    void testListarComidasVacio() throws Exception {
        when(comidaService.listarTodas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/comida/listar"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarPorId() throws Exception {
        when(comidaService.buscarPorId(1L)).thenReturn(comidaDTO);

        mockMvc.perform(get("/api/comida/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Pizza"));
    }

    @Test
    void testBuscarPorIdNoEncontrado() throws Exception {
        when(comidaService.buscarPorId(1L)).thenThrow(new ComidaNotFoundException(1L));

        mockMvc.perform(get("/api/comida/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarPorNombre() throws Exception {
        when(comidaService.buscarPorNombre("Pizza")).thenReturn(Arrays.asList(comidaDTO));

        mockMvc.perform(get("/api/comida/nombre/Pizza"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Pizza"));
    }

    @Test
    void testBuscarPorCategoria() throws Exception {
        when(comidaService.buscarPorCategoria("OTROS")).thenReturn(Arrays.asList(comidaDTO));

        mockMvc.perform(get("/api/comida/categoria/OTROS"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].categoria").value("OTROS"));
    }

    @Test
    void testActualizarComida() throws Exception {
        when(comidaService.actualizar(eq(1L), any(ComidaDTO.class))).thenReturn(comidaDTO);

        mockMvc.perform(put("/api/comida/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comidaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Comida actualizada correctamente"));
    }

    @Test
    void testActualizarComidaNoEncontrada() throws Exception {
        when(comidaService.actualizar(eq(1L), any(ComidaDTO.class)))
                .thenThrow(new ComidaNotFoundException(1L));

        mockMvc.perform(put("/api/comida/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comidaDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarComida() throws Exception {
        when(comidaService.guardar(any(ComidaDTO.class))).thenReturn(comidaDTO);

        mockMvc.perform(post("/api/comida/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comidaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Comida guardada correctamente"));
    }

    @Test
    void testGuardarComidaError() throws Exception {
        when(comidaService.guardar(any(ComidaDTO.class))).thenReturn(null);

        mockMvc.perform(post("/api/comida/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comidaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al guardar la comida"));
    }
}
