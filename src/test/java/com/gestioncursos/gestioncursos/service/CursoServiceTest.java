package com.gestioncursos.gestioncursos.service;

import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.repository.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ?
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCurso() {
        Curso curso = new Curso(0, "Java", "Nivel Intermedio");
        Curso cursoGuardado = new Curso(1, "Java", "Nivel Intermedio");

        when(cursoRepository.save(curso)).thenReturn(cursoGuardado);

        Curso resultado = cursoService.crearCurso(curso);

        assertThat(resultado.getIdCurso()).isEqualTo(1);
        verify(cursoRepository).save(curso);
    }

    @Test
    void testFindById() {
        Curso curso = new Curso(1, "Python", "Nivel Básico");
        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        Optional<Curso> resultado = cursoService.findxIdCurso(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Python");
        verify(cursoRepository).findById(1);
    }

    @Test
    void testFindAllCursos() {
        List<Curso> lista = new ArrayList<>();
        lista.add(new Curso(1, "Java", "Nivel Intermedio"));

        when(cursoRepository.findAll()).thenReturn(lista);

        List<Curso> resultado = cursoService.findAllCursos();

        assertThat(resultado).hasSize(1);
        verify(cursoRepository).findAll();
    }

    @Test
    void testEditCurso() {
        Curso cursoExistente = new Curso(1, "JavaScript", "Nivel Avanzado");
        Curso cursoActualizado = new Curso(1, "JavaScript", "Nivel Básico");

        when(cursoRepository.findById(1)).thenReturn(Optional.of(cursoExistente));
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoActualizado);

        Curso resultado = cursoService.editCurso(1, cursoActualizado);

        assertThat(resultado.getNombre()).isEqualTo("JavaScript");
        assertThat(resultado.getDescripcion()).isEqualTo("Nivel Básico");
    }

    @Test
    void testEliminarCurso() {
        Curso curso = new Curso(1, "PHP", "Nivel Básico");

        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        Optional<Curso> eliminado = cursoService.eliminarCurso(1);

        assertThat(eliminado).isPresent();
        verify(cursoRepository).deleteById(1);
    }
}
