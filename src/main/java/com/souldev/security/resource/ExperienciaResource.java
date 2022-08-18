package com.souldev.security.resource;


import com.souldev.security.dto.ExperienciaDTO;
import com.souldev.security.resource.rest.ExperienciaClient;
import com.souldev.security.services.ExperienciaService;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experiencia")
public class ExperienciaResource implements ExperienciaClient {

    @Autowired
    ExperienciaService experienciaService;

    @Override
    public ResponseEntity<JsonObject> listarExperiencias(Integer idPersona) {
        return experienciaService.listarExperiencias(idPersona);
    }

    @Override
    public ResponseEntity<JsonObject> guardarExperiencia(ExperienciaDTO e) {
        return experienciaService.guardar(e);
    }

    @Override
    public ResponseEntity<JsonObject> borrarExperiencia(Integer idExperiencia) {
        return experienciaService.eliminarExperiencia(idExperiencia);
    }

    @Override
    public ResponseEntity<JsonObject> actualizarExperiencia(Integer idExperiencia, ExperienciaDTO experiencia) {
        return experienciaService.actualizarExperiencia(idExperiencia, experiencia);
    }
}
