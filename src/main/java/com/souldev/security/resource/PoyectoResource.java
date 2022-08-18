package com.souldev.security.resource;


import com.souldev.security.dto.ProyectoDTO;
import com.souldev.security.resource.rest.ProyectoClient;
import com.souldev.security.services.ProyectoService;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/proyecto")
public class PoyectoResource implements ProyectoClient {

    @Autowired
    ProyectoService proyectoService;

    @Override
    public ResponseEntity<JsonObject> listarProyectos(Integer idPersona) { return proyectoService.listarProyectos(idPersona); }

    @Override
    public ResponseEntity<JsonObject> guardarProyecto(@Valid ProyectoDTO p) {
        return proyectoService.guardar(p);
    }

    @Override
    public ResponseEntity<JsonObject> borrarProyecto(Integer idProyecto) { return proyectoService.eliminarProyecto(idProyecto); }

    @Override
    public ResponseEntity<JsonObject> actualizarProyecto(Integer idProyecto, ProyectoDTO proyecto) { return proyectoService.actualizarExperiencia(idProyecto, proyecto); }

}
