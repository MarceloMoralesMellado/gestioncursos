package com.gestioncursos.gestioncursos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.service.CursoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CursoController.class)
class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCursos() throws Exception {
        List<Curso> cursos = Arrays.asList(
                new Curso(1, "Java", "Nivel Intermedio"),
                new Curso(2, "Spring Boot", "Nivel Avanzado")
        );

        when(cursoService.findAllCursos()).thenReturn(cursos);

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Java"))
                .andExpect(jsonPath("$[1].descripcion").value("Nivel Avanzado"));
    }

    @Test
    void testFindCursoById() throws Exception {
        Curso curso = new Curso(1, "Python", "Nivel Básico");

        when(cursoService.findxIdCurso(1)).thenReturn(Optional.of(curso));

        mockMvc.perform(get("/api/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Python"));
    }

    @Test
    void testCrearCurso() throws Exception {
        Curso curso = new Curso(0, "Docker", "Nivel Básico");
        Curso creado = new Curso(1, "Docker", "Nivel Básico");

        when(cursoService.crearCurso(any(Curso.class))).thenReturn(creado);

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCurso").value(1))
                .andExpect(jsonPath("$.nombre").value("Docker"));
    }

    @Test
    void testEditarCurso() throws Exception {
        Curso editado = new Curso(1, "NodeJS", "Nivel Intermedio");

        when(cursoService.editCurso(eq(1), any(Curso.class))).thenReturn(editado);

        mockMvc.perform(put("/api/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Nivel Intermedio"));
    }

    @Test
    void testEliminarCurso() throws Exception {
        Curso curso = new Curso(1, "SQL", "Nivel Avanzado");

        when(cursoService.eliminarCurso(1)).thenReturn(Optional.of(curso));

        mockMvc.perform(delete("/api/cursos/1"))
                .andExpect(status().isNoContent());
    }
}