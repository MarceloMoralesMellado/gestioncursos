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
@RequestMapping("/api/cursos") // TENIA UN ERROR EN ("/api/curso") Y ERA ("/api/cursos"), EL CONTROLLER TEST NO TENIA BIEN LA RUTA
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> postCurso(@RequestBody Curso curso) {
        Curso buscado = cursoService.findxIdCurso(curso.getIdCurso()).orElse(null);
        if (buscado == null) {
            return new ResponseEntity<>(cursoService.crearCurso(curso), HttpStatus.CREATED);
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
            return new ResponseEntity<>(lista, HttpStatus.OK);
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

    /*@DeleteMapping("/{idCurso}")  //ARROJA ERROR EN EL TEST CONTROLLER 200 != 204
    public ResponseEntity<Curso> deleteCurso(@PathVariable Integer idCurso) {
        Curso eliminado = cursoService.eliminarCurso(idCurso).orElse(null);
        if (eliminado != null) {
            return new ResponseEntity<>(eliminado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @DeleteMapping("/{idCurso}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Integer idCurso) {
    boolean eliminado = cursoService.eliminarCurso(idCurso).isPresent();
    return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
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
