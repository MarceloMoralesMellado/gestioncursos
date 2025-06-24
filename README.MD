### Microservicio Gestion de Cursos

----GESTION CURSOS-----------------------

Curso:
PUT: http://localhost:8083/api/curso/{idcurso}  --->(modifica solo nombre y descripción)
DELETE: http://localhost:8083/api/curso/{idcurso}
POST: http://localhost:8083/api/curso

{
    "nombre": "Python",
    "descripcion": "Python 1 Nivel Básico"
}

{
    "nombre": "Java",
    "descripcion": "Java 2 Nivel Intermedio"
}


----CREAR ROL:--------
POST: http://localhost:8081/api/rol

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

----GESTION USUARIO:--------
PUT: http://localhost:8081/api/usuario/{idusuario}  --> (modificar nombre, apellido, email, rol)
DELETE: http://localhost:8081/api/usuario/{idusuario}
POST: http://localhost:8081/api/usuario

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
-------------------------------------------------------------------------

--GESTION SOPORTE-------------------------------------------------------



----CREAR TICKET DE SOPORTE----------------
POST: localhost:8082/api/soporte

{
   
    "tituloSoporte": "Problema clase borrada",
    "descripcionSoporte": "no puedo ver mis clases",
    "estadoTicket": "Pendiente",
    "categoriaTicket": "Prioridad_Media",
    "usuario_asignado": null,
    "creadoPor": "Soporte Tecnico"

}


----ASIGNAR USUARIO AL TICKET----(Prioridad_Baja, Prioridad_Media, Prioridad_Alta)(Abierto, Cerrado, Pendiente)--------------
@PutMapping("/{idSoporte}/usuarioAsignado/{usuarioId}")

PUT: localhost:8082/api/soporte/1/usuarioAsignado/1

{
   
    "tituloSoporte": "Problema clase borrada",
    "descripcionSoporte": "no puedo ver mis clases",
    "estadoTicket": "Pendiente",
    "categoriaTicket": "Prioridad_Media",
    "usuario_asignado": null,
    "creadoPor": "Soporte Tecnico"

}


-----MODIFICAR TICKET----(Prioridad_Baja, Prioridad_Media, Prioridad_Alta)(Abierto, Cerrado, Pendiente)-------------------

PUT: localhost:8082/api/soporte/{idSoporte} 

{
   
    "tituloSoporte": "Problemas acceso a clases",
    "descripcionSoporte": "no puede acceder a las clases",
    "estadoTicket": "Abierto",
    "categoriaTicket": "Prioridad_Baja"    

}


-----CONSULTA TICKET POR ESTADO----(pendiente, abierto, cerrado)-------------
GET: localhost:8082/api/soporte?estado=pendiente
GET: localhost:8082/api/soporte?estado=abierto
GET: localhost:8082/api/soporte?estado=cerrado



-----CONSULTA TODOS LOS TICKET -------------

GET: localhost:8082/api/soporte



-----ELIMINAR TICKET

DELETE: localhost:8082/api/soporte/{idSoporte}


CORRECCION PROFESOR:

-Con el usuario que queda en el aire cuando uno elimina un ticket.
-No eliminar los ticket (DELETE), cambiarlos de estado solamente, así me puede quedar un registro por si se tiene que retomar nuevamente el problema.