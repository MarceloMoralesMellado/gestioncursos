package com.gestioncursos.gestioncursos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestioncursos.gestioncursos.assamblers.CursoModelAssembler;
import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.service.CursoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private CursoModelAssembler assembler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Curso curso;
 
    @BeforeEach
    void setUp() {
        curso = new Curso(1, "Java", "Nivel Intermedio");

        when(assembler.toModel(any(Curso.class))).thenAnswer(invocation -> {
            Curso c = invocation.getArgument(0);
            return EntityModel.of(c,
                linkTo(CursoController.class).slash(c.getIdCurso()).withSelfRel(),
                linkTo(CursoController.class).withRel("cursos"),
                linkTo(CursoController.class).withRel("delete")
            );
        });
    }

    @Test
    void testPostCurso_creaNuevo() throws Exception {
        Curso nuevo = new Curso(1, "JavaScript", "Nivel Avanzado");

        when(cursoService.findxIdCurso(1)).thenReturn(Optional.empty());
        when(cursoService.crearCurso(any(Curso.class))).thenReturn(nuevo);


        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idCurso").value(1))
            .andExpect(jsonPath("$.nombre").value("JavaScript"))
            .andExpect(jsonPath("$.descripcion").value("Nivel Avanzado"))
            .andExpect(jsonPath("$._links.self.href").exists())
            .andExpect(jsonPath("$._links.cursos.href").exists());
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

        mockMvc.perform(get("/api/cursos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.cursoList[0].idCurso").value(1))
            .andExpect(jsonPath("$._embedded.cursoList[0].nombre").value("Java"))
            .andExpect(jsonPath("$._embedded.cursoList[0].descripcion").value("Nivel Intermedio"))
            .andExpect(jsonPath("$._embedded.cursoList[0]._links.self.href").exists())
            .andExpect(jsonPath("$._embedded.cursoList[0]._links.cursos.href").exists())
            .andExpect(jsonPath("$._links.self.href").exists()); 
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
                .andExpect(jsonPath("$.nombre").value("Java"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.cursos.href").exists());
    }

    @Test
    void testGetCursoById_noEncontrado() throws Exception {
        when(cursoService.findxIdCurso(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cursos/99"))
                .andExpect(status().isNotFound());
    }    

    @Test
    void testPutCurso_actualizaDescripcion() throws Exception {
        Curso actualizado = new Curso(1, "Java", "Nivel Básico");
        when(cursoService.editCurso(eq(1), any(Curso.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idCurso").value(1))
            .andExpect(jsonPath("$.nombre").value("Java"))
            .andExpect(jsonPath("$.descripcion").value("Nivel Básico"))
            .andExpect(jsonPath("$._links.self.href").exists())
            .andExpect(jsonPath("$._links.cursos.href").exists());
    }
    
    @Test
    void testPutCurso_noEncontrado() throws Exception {
        Curso actualizado = new Curso(1, "Java", "Nivel Intermedio");
        when(cursoService.editCurso(eq(1), any(Curso.class))).thenReturn(null);

        mockMvc.perform(put("/api/cursos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
    }

    @Test          
    void testDeleteCurso_encontrado() throws Exception {
        when(cursoService.eliminarCurso(1)).thenReturn(Optional.of(curso));
        //when(assembler.toModel(any(Curso.class))).thenReturn(cursoModel);

        mockMvc.perform(delete("/api/cursos/1"))
            .andExpect(status().isNoContent());
                
    }

    @Test
    void testDeleteCurso_noEncontrado() throws Exception {
        when(cursoService.eliminarCurso(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/cursos/1"))
                .andExpect(status().isNotFound());
    }
}
 