package com.gestioncursos.gestioncursos.controller;

import java.util.List;

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

import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.service.CursoService;

@RestController
@RequestMapping("/api/curso")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> postCurso(@RequestBody Curso curso) {
        Curso buscado = cursoService.findxIdCurso(curso.getIdCurso()).orElse(null);
        if (buscado == null) {
            return new ResponseEntity<>(cursoService.crearCurso(curso), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getCurso() {
        List<Curso> lista = cursoService.findAllCursos();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/{idCurso}")
    public ResponseEntity<Curso> putCurso(@PathVariable Integer idCurso, @RequestBody Curso curso) {
        Curso actualizado = cursoService.editCurso(idCurso, curso);
        if (actualizado != null) {
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idCurso}")
    public ResponseEntity<Curso> deleteCurso(@PathVariable Integer idCurso) {
        Curso eliminado = cursoService.eliminarCurso(idCurso).orElse(null);
        if (eliminado != null) {
            return new ResponseEntity<>(eliminado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idCurso}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Integer idCurso) {
        Curso curso = cursoService.findxIdCurso(idCurso).orElse(null);//.orElse controla el tipo Optional<Curso>
        if (curso != null) {
            return ResponseEntity.ok(curso);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
