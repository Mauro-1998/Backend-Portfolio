package com.souldev.security.resource.rest;

import com.souldev.security.dto.ProyectoDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public interface ProyectoClient {

    @GetMapping(value = "/listar-proyectos/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista todos los proyectos de un usuario dado.")
    @ResponseBody
    ResponseEntity<JsonObject> listarProyectos(@PathVariable @Min(1) Integer idPersona);

    @PostMapping(value = "/guardar-proyectos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo registra a un nuevo proyecto para un usuario.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> guardarProyecto(@Valid @RequestBody ProyectoDTO p);

    @DeleteMapping(value = "/eliminar-proyecto/{idProyecto}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo eliminará un proyecto de la base de datos")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> borrarProyecto(@PathVariable @Min(1) Integer idProyecto);

    @PutMapping(value = "/modificar-proyecto/{idProyecto}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo actualizará un proyecto de una persona en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> actualizarProyecto(@PathVariable @Min(1) Integer idProyecto, @Valid @RequestBody ProyectoDTO proyecto);

}
