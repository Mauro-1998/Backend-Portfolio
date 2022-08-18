package com.souldev.security.services;


import com.souldev.security.dto.CursoDTO;
import com.souldev.security.entities.Curso;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.mapper.CursoMapper;
import com.souldev.security.repositories.CursoRepository;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

@Service
public class CursoService {
    @Autowired
    CursoRepository cursoRepository;

    CursoMapper cursoMapper = new CursoMapper();

    Logger logger = Logger.getLogger(getClass().getName());


    public ResponseEntity<JsonObject> listarCursos() {

        JsonObject response = new JsonObject();
        Set<Curso> cursos = cursoRepository.findAll();

        response.put(EstadoMensaje.SUCCESS.toString(), cursos);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<JsonObject> guardar(CursoDTO c) {
        JsonObject response = new JsonObject();
        if (cursoRepository.findByNombre(c.getNombre()) == null) {
            Curso cur = new Curso();
            cur.setNombre(c.getNombre());
            Curso curso = cursoRepository.save(cur);
            logger.info("CURSO: " + curso);
            response.put(EstadoMensaje.SUCCESS.toString(), curso);
            return ResponseEntity.status(200).body(response);
        } else {
            response.put(EstadoMensaje.ERROR.toString(), "Ya hay un curso registrado con ese nombre");
            return ResponseEntity.status(409).body(response);
        }
    }

    public ResponseEntity<JsonObject> eliminarCurso(Integer id) {
        JsonObject response = new JsonObject();
        Optional<Curso> entityOptional = cursoRepository.findById(id);
        if (entityOptional.isPresent()) {
            Curso entity = entityOptional.get();
            cursoRepository.delete(entity);
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<JsonObject> actualizarCurso(Integer id, CursoDTO c) {
        JsonObject response = new JsonObject();
        return cursoRepository.findById(id).
                map(curso -> {
                    if (!curso.getNombre().equalsIgnoreCase(c.getNombre())) {
                        if (cursoRepository.findByNombre(c.getNombre()) == null) {
                            curso.setNombre(c.getNombre().toUpperCase());
                        } else {
                            response.put(EstadoMensaje.ERROR.toString(), "Ya hay un curso registrado con dicho nombre");
                            return ResponseEntity.status(409).body(response);
                        }
                    }
                    response.put(EstadoMensaje.SUCCESS.toString(), cursoRepository.save(curso));
                    return ResponseEntity.status(200).body(response);
                })
                .orElseGet(() -> {
                    response.put(EstadoMensaje.ERROR.toString(), "No se pueden actualizar los datos porque no hay un curso registrado con ese nombre");
                    return ResponseEntity.status(409).body(response);
                });
    }
}
