package com.souldev.security.resource.rest;


import com.souldev.security.dto.PersonaCarreraDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

public interface PersonaCarreraClient {

    @PostMapping(value = "/vincular-carrera", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo vinculará a una persona con una carrera.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> vincularCarrera(@Valid @RequestBody PersonaCarreraDTO p);


    @DeleteMapping(value = "/desvincular-carrera", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo desvinculará a una persona de una carrera.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> desvincularCarrera(@Min(1) @QueryParam("idPersona") Integer idPersona, @Min(1) @QueryParam("idCarrera") Integer idCarrera);


    @GetMapping(value = "/listar-carreras-persona", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todos las carreras realizadas por una persona dada.")
    @ResponseBody
    ResponseEntity<Set> listarCarrerasPorPersona(@Min(1) @QueryParam("idPersona") Integer idPersona);


    @GetMapping(value = "/listar-personas-carrera", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todas las personas de una carrera dada.")
    @ResponseBody
    ResponseEntity<JsonObject> listarPersonasPorCarrera(@Min(1) @QueryParam("idCarrera") Integer idCarrera);

}
