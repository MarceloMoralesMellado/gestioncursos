package com.gestioncursos.gestioncursos.assamblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.gestioncursos.gestioncursos.model.Curso;
import com.gestioncursos.gestioncursos.controller.CursoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

    @Override
    public EntityModel<Curso> toModel(Curso curso) {
        return EntityModel.of(curso,
            linkTo(methodOn(CursoController.class).getCurso()).withRel("cursos"),
            linkTo(methodOn(CursoController.class).getCursoById(curso.getIdCurso())).withSelfRel(),
            linkTo(methodOn(CursoController.class).deleteCurso(curso.getIdCurso())).withRel("delete")
        );
            
            
    }
}