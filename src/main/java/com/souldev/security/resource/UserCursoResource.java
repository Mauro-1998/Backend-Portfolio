package com.souldev.security.resource;


import com.souldev.security.dto.UserCursoDTO;
import com.souldev.security.mapper.PersonaCursoMapper;
import com.souldev.security.resource.rest.PersonaCursoClient;
import com.souldev.security.services.UserCursoService;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persona-curso")
public class UserCursoResource implements PersonaCursoClient {

    @Autowired
    UserCursoService userCursoService;

    Logger logger = Logger.getLogger(getClass().getName());

    PersonaCursoMapper mapper = new PersonaCursoMapper();

    @Override
    public ResponseEntity<JsonObject> vincularCurso(UserCursoDTO p) { return userCursoService.agregarCurso(p); }

    @Override
    public ResponseEntity<JsonObject> desvincularCurso(Integer idPersona, Integer idCurso) { return userCursoService.quitarCurso(idPersona,idCurso); }

    @Override
    public ResponseEntity<JsonObject> listarCursosPorPersona(Integer idPersona) { return userCursoService.listarCursosPorPersona(idPersona); }

    @Override
    public ResponseEntity<JsonObject> listarPersonasPorCurso(Integer idCurso) { return userCursoService.listarPersonasPorCurso(idCurso); }


}
