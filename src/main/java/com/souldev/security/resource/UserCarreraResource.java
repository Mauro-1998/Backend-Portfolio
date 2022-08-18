package com.souldev.security.resource;


import com.souldev.security.dto.PersonaCarreraDTO;
import com.souldev.security.resource.rest.PersonaCarreraClient;
import com.souldev.security.services.PersonaCarreraService;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persona-carrera")
public class UserCarreraResource implements PersonaCarreraClient {

    @Autowired
    PersonaCarreraService personaCarreraService;

    Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public ResponseEntity<JsonObject> vincularCarrera(PersonaCarreraDTO p) {
        return personaCarreraService.agregarCarrera(p);
    }

    @Override
    public ResponseEntity<JsonObject> desvincularCarrera(Integer idPersona, Integer idCarrera) {
        return personaCarreraService.quitarCarrera(idPersona,idCarrera);
    }

    @Override
    public ResponseEntity<JsonObject> listarCarrerasPorPersona(Integer idPersona) {
        return personaCarreraService.listarCarrerasPorPersona(idPersona);
    }

    @Override
    public ResponseEntity<JsonObject>listarPersonasPorCarrera(Integer idCarrera) {
        return personaCarreraService.listarPersonasPorCarrera(idCarrera);
    }
}
