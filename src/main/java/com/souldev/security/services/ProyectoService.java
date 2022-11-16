package com.souldev.security.services;


import com.souldev.security.dto.ProyectoDTO;
import com.souldev.security.entities.Proyecto;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.mapper.ProyectoMapper;
import com.souldev.security.repositories.ProyectoRepository;
import com.souldev.security.security.entities.User;
import com.souldev.security.security.respositories.UserRepository;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import static com.souldev.security.common.Functions.ucFirst;

@Service
public class ProyectoService {

    @Autowired
    ProyectoRepository proyectoRepository;

    @Autowired
    UserRepository personaRepository;

    Logger logger = Logger.getLogger(getClass().getName());

    ProyectoMapper mapper = new ProyectoMapper();


    public ResponseEntity<JsonObject> listarProyectos(Integer id) {
        JsonObject response = new JsonObject();
        if (personaRepository.findById(id).isPresent()) {
            Set<Proyecto> proyectos = proyectoRepository.findAllUserProjects(id);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), proyectos));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }


    public ResponseEntity<JsonObject> guardar(ProyectoDTO p) {
        JsonObject response = new JsonObject();
        Optional<User> personaOptional = personaRepository.findById(p.getIdPersona());
        if (personaOptional.isPresent()) {
            User per = personaOptional.get();
            if (proyectoRepository.findProyectoByNombreAndIdPersona(p.getNombre().toLowerCase(), p.getIdPersona()) == null) {
                p.setNombre(p.getNombre().toLowerCase());
                p.setDescripcion(p.getDescripcion().toLowerCase());
                Proyecto proy = new Proyecto();
                proy.setUser(per);
                proy.setDescripcion(ucFirst(p.getDescripcion()));
                proy.setNombre(ucFirst(p.getNombre()));
                proy.setUrlFoto(p.getFotoURL());
                Proyecto proyecto = proyectoRepository.save(proy);
                per.addProyecto(proyecto);
                logger.info("Se insertó el dato correctamente");
                response.put(EstadoMensaje.SUCCESS.toString(), proyecto);
                return ResponseEntity.status(200).body(response);
            } else {
                logger.error("Ya registró su proyecto en ese nombre");
                response.put(EstadoMensaje.ERROR.toString(), "Ya registró su proyecto en ese nombre");
                return ResponseEntity.status(409).body(response);
            }
        } else {
            logger.error("La persona informada no está registrada");
            response.put(EstadoMensaje.ERROR.toString(), "La persona informada no está registrada");
            return ResponseEntity.status(404).body(response);
        }
    }

    public ResponseEntity<JsonObject> eliminarProyecto(Integer id) {
        JsonObject response = new JsonObject();
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        if (proyecto.isPresent()) {
            Proyecto proy = proyecto.get();
            proy.getUser().removeProyecto(proy);
            proyectoRepository.delete(proyecto.get());

        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<JsonObject> actualizarExperiencia(Integer id, ProyectoDTO p) {
        JsonObject response = new JsonObject();
        return proyectoRepository.findById(id).
                map(proyecto -> {
                    if (!proyecto.getNombre().equalsIgnoreCase(p.getNombre()))
                        proyecto.setNombre(ucFirst(p.getNombre()));
                    if (!proyecto.getDescripcion().equalsIgnoreCase(p.getDescripcion()))
                        proyecto.setDescripcion(ucFirst(p.getDescripcion()));
                    if (!proyecto.getUrlFoto().equals(p.getFotoURL()))
                        proyecto.setUrlFoto(p.getFotoURL());

                    response.put(EstadoMensaje.SUCCESS.toString(), proyectoRepository.save(proyecto));
                    return ResponseEntity.status(200).body(response);
                })
                .orElseGet(() -> {
                    response.put(EstadoMensaje.ERROR.toString(), "No se pueden actualizar los datos porque la experiencia no esta registrada");
                    return ResponseEntity.status(409).body(response);
                });
    }
}
