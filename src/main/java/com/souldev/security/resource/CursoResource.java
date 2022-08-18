package com.souldev.security.resource;


import com.souldev.security.dto.CursoDTO;
import com.souldev.security.resource.rest.CursoClient;
import com.souldev.security.services.CursoService;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
public class CursoResource implements CursoClient {


    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    CursoService cursoService;

    @Override
    public ResponseEntity<JsonObject> listarCursos() {
        return cursoService.listarCursos();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JsonObject> guardarCurso(CursoDTO c) {
        return cursoService.guardar(c);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JsonObject> borrarCurso(Integer id) {
        return cursoService.eliminarCurso(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JsonObject> actualizarCurso(Integer id, CursoDTO curso) { return cursoService.actualizarCurso(id, curso); }
}
