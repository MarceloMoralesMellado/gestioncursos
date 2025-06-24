package com.gestioncursos.gestioncursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.repository.CursoRepository;

@Service
public class CursoService {   
   

    @Autowired
    private CursoRepository cursoRepository;

    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> findxIdCurso(Integer idCurso) {
        return cursoRepository.findById(idCurso);
    }

    public List<Curso> findAllCursos() {
        return cursoRepository.findAll();
    }

    public Curso editCurso(Integer idCurso, Curso curso) {
        Optional<Curso> cursoExistente = cursoRepository.findById(idCurso);
        if (cursoExistente.isPresent()) {
            Curso cursoActualizado = cursoExistente.get();
            cursoActualizado.setNombre(curso.getNombre());
            cursoActualizado.setDescripcion(curso.getDescripcion());

            return cursoRepository.save(cursoActualizado);
        }
        return null;
    }

    public Optional<Curso> eliminarCurso(int idCurso) {
        Optional<Curso> curso = cursoRepository.findById(idCurso); // cursoRepository.findById(idUsuario) ¿arroja error, porque? ---> error de sintaxis
        if (curso != null) { // Aquí debería ser (curso.isPresent()) ???
            cursoRepository.deleteById(idCurso);
            return curso;
        }
        return Optional.empty(); // Debes devolver Optional.empty() en lugar de null
    }

}
