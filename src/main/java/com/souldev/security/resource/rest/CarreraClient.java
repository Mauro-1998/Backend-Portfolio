package com.souldev.security.resource.rest;

import com.souldev.security.dto.CarreraDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public interface CarreraClient {

    @GetMapping(value = "/listar-carreras", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todas las carreras de la base de datos.")
    @ResponseBody
    ResponseEntity<JsonObject> listarCarreras();

    @PostMapping(value = "/guardar-carrera", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo registra una nueva carrera en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> guardarCarrera(@Valid @RequestBody CarreraDTO c);

    @DeleteMapping(value = "/eliminar-carrera/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo eliminará una carrera de la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> borrarCarrera(@PathVariable @Min(1) Integer id);

    @PutMapping(value = "/modificar-carrera/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo actualizará los datos de una carrera en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> actualizarCarrera(@PathVariable @Min(1) Integer id, @Valid @RequestBody CarreraDTO c);

}
