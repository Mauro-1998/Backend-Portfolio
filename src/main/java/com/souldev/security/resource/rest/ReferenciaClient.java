package com.souldev.security.resource.rest;


import com.souldev.security.dto.ReferenciaDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public interface ReferenciaClient {
    @GetMapping(value = "/listar-referencias/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista todas las referencias de un usuario dado.")
    @ResponseBody
    ResponseEntity<JsonObject> listarReferencias(@PathVariable @Min(1) Integer idPersona);

    @PostMapping(value = "/guardar-referencias", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo registra una nueva referencia para un usuario.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> guardarReferencia(@Valid @RequestBody ReferenciaDTO r);

    @DeleteMapping(value = "/eliminar-referencia/{idReferencia}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo eliminará una referencia de la base de datos")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> borrarReferencia(@PathVariable @Min(1) Integer idReferencia);

    @PutMapping(value = "/modificar-referencia/{idReferencia}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo actualizará un proyecto de una persona en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> actualizarReferencia(@PathVariable @Min(1) Integer idReferencia, @Valid @RequestBody ReferenciaDTO r);

}
