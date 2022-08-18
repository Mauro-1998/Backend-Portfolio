package com.souldev.security.resource.rest;


import com.souldev.security.dto.UserCursoDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

public interface PersonaCursoClient {

    @PostMapping(value = "/vincular-curso", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo vinculará a una persona con un curso.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> vincularCurso(@Valid @RequestBody UserCursoDTO p);


    @DeleteMapping(value = "/desvincular-curso", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo desvinculará a una persona de un curso.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> desvincularCurso(@Min(1) @QueryParam("idPersona") Integer idPersona, @Min(1) @QueryParam("idCurso") Integer idCurso);


    @GetMapping(value = "/listar-cursos-persona", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todos los cursos realizados por una persona dada.")
    @ResponseBody
    ResponseEntity<JsonObject> listarCursosPorPersona(@Min(1) @QueryParam("idPersona") Integer idPersona);


    @GetMapping(value = "/listar-personas-curso", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todas las personas de un curso dado.")
    @ResponseBody
    ResponseEntity<JsonObject> listarPersonasPorCurso(@Min(1) @QueryParam("idCurso") Integer idCurso);

}
