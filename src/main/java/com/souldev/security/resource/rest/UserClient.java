package com.souldev.security.resource.rest;



import com.souldev.security.dto.PersonaDTO;
import io.swagger.annotations.ApiOperation;
import io.vertx.core.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;


public interface UserClient {
    @GetMapping(value = "/listar-personas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo lista a todas las personas de la base de datos.")
    @ResponseBody
    ResponseEntity<JsonObject> listarPersonas();


    @DeleteMapping(value = "/eliminar-persona/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo eliminará una persona de la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> borrarPersona(@PathVariable @Min(1) Integer id);

    @PutMapping(value = "/modificar-persona/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Este metodo actualizará los datos de una persona en la base de datos.")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<JsonObject> actualizarPersona(@PathVariable @Min(1) Integer id, @Valid @RequestBody PersonaDTO persona);



}
