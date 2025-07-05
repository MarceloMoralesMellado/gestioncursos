package com.gestioncursos.gestioncursos.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestioncursos.gestioncursos.assamblers.CursoModelAssembler;
import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.service.CursoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/cursos") 
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Curso>> postCurso(@RequestBody Curso curso) {
        Curso buscado = cursoService.findxIdCurso(curso.getIdCurso()).orElse(null);
        if (buscado == null) {
            Curso creado = cursoService.crearCurso(curso);
            EntityModel<Curso> cursoModel = assembler.toModel(creado);
            return ResponseEntity
                .created(linkTo(methodOn(CursoController.class).getCursoById(creado.getIdCurso())).toUri())
                .body(cursoModel);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Curso>>> getCurso() {
        List<Curso> lista = cursoService.findAllCursos();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Curso>> cursos = lista.stream()
            .map(assembler::toModel)
            .toList();

        return ResponseEntity.ok(
            CollectionModel.of(cursos,
                linkTo(methodOn(CursoController.class).getCurso()).withSelfRel()
            )
        );
    }

    @GetMapping("/{idCurso}")
    public ResponseEntity<EntityModel<Curso>> getCursoById(@PathVariable Integer idCurso) {
        return cursoService.findxIdCurso(idCurso)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idCurso}")
    public ResponseEntity<EntityModel<Curso>> putCurso(@PathVariable Integer idCurso, @RequestBody Curso curso) {
        Curso actualizado = cursoService.editCurso(idCurso, curso);
        if (actualizado != null) {
            EntityModel<Curso> cursoModel = assembler.toModel(actualizado);
            return ResponseEntity.ok(cursoModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }   

    @DeleteMapping("/{idCurso}")  
    public ResponseEntity<Void> deleteCurso(@PathVariable Integer idCurso) {
    boolean eliminado = cursoService.eliminarCurso(idCurso).isPresent();
    return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    
}


