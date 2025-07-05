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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // se utiliza para habilitar la extensión de Mockito en pruebas unitarias
class CursoServiceTest {

    @Mock // para crear objetos simulados (mocks) de clases o interfaces, en pruebas unitarias
    private CursoRepository cursoRepository;

    @InjectMocks // para inyectar automáticamente los mocks en el objeto que estás probando.
    private CursoService cursoService;

    @BeforeEach // marca un método que debe ejecutarse antes de cada uno de los métodos de prueba (@Test).
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
    
    //EDITAR CURSO SI NO EXISTE
    @Test 
    void testEditCurso_noExiste() {
        Curso actualizado = new Curso(1, "JavaScript", "Nivel Avanzado");
        when(cursoRepository.findById(1)).thenReturn(Optional.empty());
        Curso resultado = cursoService.editCurso(1, actualizado);
        assertThat(resultado).isNull();
    }

    @Test
    void testEliminarCurso() {
        Curso curso = new Curso(1, "Java", "Nivel Intermedio");

        when(cursoRepository.findById(1)).thenReturn(Optional.of(curso));

        Optional<Curso> eliminado = cursoService.eliminarCurso(1);

        assertThat(eliminado).isPresent();
        verify(cursoRepository).deleteById(1);
    }

    //ELIMINAR CURSO SI NO EXISTE
    @Test 
    void testEliminarUsuario_noExiste() {
        when(cursoRepository.findById(99)).thenReturn(Optional.empty());
        Optional<Curso> eliminado = cursoService.eliminarCurso(99);
        assertThat(eliminado).isEmpty();
        verify(cursoRepository, never()).deleteById(anyInt());
    }
}
