package com.souldev.security.resource.rest;


import com.souldev.security.dto.ExperienciaDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public interface ExperienciaClient {
    @GetMapping(value = "/listar-experiencia/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todas las experiencias de un usuario dado.")
    @ResponseBody
    ResponseEntity<JsonObject> listarExperiencias(@PathVariable @Min(1) Integer idPersona);

    @PostMapping(value = "/guardar-experiencia", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo registra a una nueva experiencia para un usuario.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> guardarExperiencia(@Valid @RequestBody ExperienciaDTO e);

    @DeleteMapping(value = "/eliminar-experiencia/{idExperiencia}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo eliminará una experiencia de la base de datos")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> borrarExperiencia(@PathVariable @Min(1) Integer idExperiencia);

    @PutMapping(value = "/modificar-experiencia/{idExperiencia}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo actualizará una experiencia de una persona en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> actualizarExperiencia(@PathVariable @Min(1) Integer idExperiencia, @Valid @RequestBody ExperienciaDTO experiencia);

}
