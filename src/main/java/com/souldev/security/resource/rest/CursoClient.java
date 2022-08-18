package com.souldev.security.resource.rest;

import com.souldev.security.dto.CursoDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public interface CursoClient {

    @GetMapping(value = "/listar-cursos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todas los cursos de la base de datos.")
    @ResponseBody
    ResponseEntity<JsonObject> listarCursos();

    @PostMapping(value = "/guardar-curso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo registra un nuevo curso en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> guardarCurso(@Valid @RequestBody CursoDTO p);

    @DeleteMapping(value = "/eliminar-curso/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo eliminará un curso de la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> borrarCurso(@PathVariable @Min(1) Integer id);

    @PutMapping(value = "/modificar-curso/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo actualizará los datos de un curso en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> actualizarCurso(@PathVariable @Min(1) Integer id, @Valid @RequestBody CursoDTO curso);
}
