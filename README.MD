# Microservicio: Gestión de Cursos

## Endpoints Principales

### Gestión de Cursos

| Método | Endpoint                                 | Descripción                                 |
|--------|------------------------------------------|---------------------------------------------|
| POST   | `/api/curso`                             | Crear un nuevo curso                        |
| PUT    | `/api/curso/{idcurso}`                   | Modificar nombre y descripción de un curso   |
| DELETE | `/api/curso/{idcurso}`                   | Eliminar un curso                           |

#### Ejemplo de Request (POST/PUT)
```json
{
  "nombre": "Python",
  "descripcion": "Python 1 Nivel Básico"
}

{
  "nombre": "Java",
  "descripcion": "Java 2 Nivel Intermedio"
}
```

---

### Gestión de Roles

| Método | Endpoint              | Descripción         |
|--------|-----------------------|---------------------|
| POST   | `/api/rol`            | Crear un nuevo rol  |

#### Ejemplo de Request
```json
{
  "idRol": 1,
  "nombreRol": "Profesor",
  "funcion": "Imparte clases en distintas asignaturas"
}

{
  "idRol": 2,
  "nombreRol": "Alumno",
  "funcion": "Participa en las clases y rinde evaluaciones"
}
```

---

### Gestión de Usuarios

| Método | Endpoint                                 | Descripción                                 |
|--------|------------------------------------------|---------------------------------------------|
| POST   | `/api/usuario`                           | Crear un nuevo usuario                      |
| PUT    | `/api/usuario/{idusuario}`               | Modificar nombre, apellido, email y rol     |
| DELETE | `/api/usuario/{idusuario}`               | Eliminar un usuario                         |

#### Ejemplo de Request
```json
{
  "idUsuario": 1,
  "nombre": "Camila",
  "apellido": "Rojas",
  "email": "camila.rojas@colegio.cl",
  "rol": {
    "idRol": 1
  }
}

{
  "idUsuario": 2,
  "nombre": "Ignacio",
  "apellido": "Muñoz",
  "email": "ignacio.munoz@colegio.cl",
  "rol": {
    "idRol": 2
  }
}
```

---

### Gestión de Soporte

| Método | Endpoint                                                        | Descripción                                 |
|--------|-----------------------------------------------------------------|---------------------------------------------|
| POST   | `/api/soporte`                                                  | Crear ticket de soporte                     |
| PUT    | `/api/soporte/{idSoporte}/usuarioAsignado/{usuarioId}`          | Asignar usuario a ticket                    |
| PUT    | `/api/soporte/{idSoporte}`                                      | Modificar ticket                            |
| GET    | `/api/soporte?estado=pendiente|abierto|cerrado`                 | Consultar tickets por estado                |
| GET    | `/api/soporte`                                                  | Consultar todos los tickets                 |
| DELETE | `/api/soporte/{idSoporte}`                                      | Eliminar ticket (cambiar estado, no borrar) |

#### Ejemplo de Request (POST/PUT)
```json
{
  "tituloSoporte": "Problema clase borrada",
  "descripcionSoporte": "no puedo ver mis clases",
  "estadoTicket": "Pendiente",
  "categoriaTicket": "Prioridad_Media",
  "usuario_asignado": null,
  "creadoPor": "Soporte Tecnico"
}
```

#### Nota del Profesor
> No eliminar los tickets (DELETE), solo cambiar su estado para mantener el registro.

---

## Pruebas Unitarias

El microservicio cuenta con pruebas unitarias para los controladores y servicios principales. Puedes consultar el registro y resultados de las pruebas en la siguiente plantilla:

- [Plantilla Registro Pruebas Unitarias](https://docs.google.com/spreadsheets/d/1uK9pYxZMLNKzUvrn0RiBoK9UOOQIiOXfXNgxnNymPJs/edit?usp=sharing)

Las pruebas cubren:
- Creación, edición, consulta y eliminación de cursos
- Endpoints REST de controladores
- Lógica de negocio en servicios

---

## Notas
- El microservicio utiliza MySQL como base de datos.
- Swagger UI disponible en `/doc/swagger-ui.html` y también en [swagger-ui](http://localhost:8083/doc/swagger-ui/index.html#/) para documentación interactiva (solo accesible con el microservicio levantado).
- El puerto por defecto es `8083`.