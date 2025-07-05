package com.gestioncursos.gestioncursos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.service.CursoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @MockBean
    //private CursoModelAssembler assembler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Curso curso;
 
    @BeforeEach
    void setUp() {
        curso = new Curso(1, "Java", "Nivel Intermedio");
        // Aquí puedes inicializar otros objetos necesarios para las pruebas
    }   

    @Test
    void testPostCurso_creaNuevo() throws Exception {
        Curso nuevo = new Curso(1, "JavaScript", "Nivel Avanzado");
        when(cursoService.findxIdCurso(null)).thenReturn(Optional.empty());
        when(cursoService.crearCurso(any(Curso.class))).thenReturn(curso);
        //when(assembler.toModel(any(Curso.class))).thenReturn(cursoModel);

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCurso").value(1));
    }

    @Test
    void testPostCurso_yaExiste() throws Exception {
        when(cursoService.findxIdCurso(1)).thenReturn(Optional.of(curso));

        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(curso)))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetAllCursos_conDatos() throws Exception {
        when(cursoService.findAllCursos()).thenReturn(List.of(curso));
        //when(assembler.toModel(any(Curso.class))).thenReturn(cursoModel);

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cursoList[0].idCursos").value(1));
    }

    @Test
    void testGetAllCursos_listaVacia() throws Exception {
        when(cursoService.findAllCursos()).thenReturn(List.of());

        mockMvc.perform(get("/api/cursos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCursoById_encontrado() throws Exception {
        when(cursoService.findxIdCurso(1)).thenReturn(Optional.of(curso));
        //when(assembler.toModel(any(Curso.class))).thenReturn(cursoModel);

        mockMvc.perform(get("/api/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ana@mail.com"));  //OJO AQUI CON EL CORREO, HAY QUE CAMBIARLO !!!!!!!!!!!!!!!!
    }

    @Test
    void testGetCursoById_noEncontrado() throws Exception {
        when(cursoService.findxIdCurso(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/curso/99"))
                .andExpect(status().isNoContent());
    }    

    @Test
    void testPutCurso_actualizaDescripcion() throws Exception {
        Curso actualizado = new Curso(1, "Python", "Nivel Básico");
        when(cursoService.editCurso(eq(1), any(Curso.class))).thenReturn(actualizado);
        //when(assembler.toModel(any(Curso.class))).thenReturn(EntityModel.of(actualizado));

        mockMvc.perform(put("/api/curso/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("elena@mail.com")); //OJO AQUI CON EL CORREO, HAY QUE CAMBIARLO !!!!!!!!!!!!!!!!
    }
    
    @Test
    void testPutCurso_noEncontrado() throws Exception {
        Curso actualizado = new Curso(1, "Java", "Nivel Intermedio");
        when(cursoService.editCurso(eq(1), any(Curso.class))).thenReturn(null);

        mockMvc.perform(put("/api/curso/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCurso_encontrado() throws Exception {
        when(cursoService.eliminarCurso(1)).thenReturn(Optional.of(curso));
        //when(assembler.toModel(any(Curso.class))).thenReturn(cursoModel);

        mockMvc.perform(delete("/api/curso/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCurso").value(1));
    }

    @Test
    void testDeleteCurso_noEncontrado() throws Exception {
        when(cursoService.eliminarCurso(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/curso/1"))
                .andExpect(status().isNotFound());
    }
}
 