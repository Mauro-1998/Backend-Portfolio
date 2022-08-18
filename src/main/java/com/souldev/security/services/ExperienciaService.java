package com.souldev.security.services;


import com.souldev.security.dto.ExperienciaDTO;
import com.souldev.security.entities.Experiencia;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.mapper.ExperienciaMapper;
import com.souldev.security.repositories.ExperienciaRepository;
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
public class ExperienciaService {
    @Autowired
    ExperienciaRepository experienciaRepository;

    @Autowired
    UserRepository userRepository;

    Logger logger = Logger.getLogger(getClass().getName());

    ExperienciaMapper mapper = new ExperienciaMapper();



    public ResponseEntity<JsonObject> listarExperiencias(Integer id) {
        JsonObject response = new JsonObject();
        if (userRepository.findById(id).isPresent()) {
            Set<Experiencia> experiencias = experienciaRepository.findAllUserExperiences(id);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), experiencias));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }

    public ResponseEntity<JsonObject> guardar(ExperienciaDTO e) {
        logger.info("GUARDANDO EXPERIENCIA...");
        JsonObject response = new JsonObject();
        Optional<User> personaOptional = userRepository.findById(e.getIdPersona());
        if (personaOptional.isPresent()) {
            User p = personaOptional.get();
            if (experienciaRepository.findByEmpresaAndPuestoAndIdPersona(e.getEmpresa().toLowerCase(), e.getPuesto().toLowerCase(), e.getIdPersona()) == null) {
                logger.info("NO EXISTE EXPERIENCIA");
                e.setEmpresa(ucFirst(e.getEmpresa()));
                e.setDescripcion(ucFirst(e.getDescripcion()));
                e.setPuesto(ucFirst(e.getPuesto()));
                Experiencia exp = new Experiencia();
                exp.setActual(e.getActual());
                exp.setDescripcion(e.getDescripcion());
                exp.setEmpresa(e.getEmpresa());
                exp.setFin(e.getFin());
                exp.setInicio(e.getInicio());
                exp.setPuesto(e.getPuesto());
                exp.setUser(p);
                logger.info("PASA MAPEO A ENTIDAD: " + exp.getUser().getId());
                //logger.info("PersonaID: " + mapper.toEntity(e).getUser().getId());
                Experiencia experiencia = experienciaRepository.save(exp);
                p.addExperiencia(experiencia);
                logger.info("VAMOS GUARDAR LA EXPERIENCIA: " + experiencia);
                response.put(EstadoMensaje.SUCCESS.toString(), "Se insertó el dato correctamente");
                return ResponseEntity.status(200).body(response);
            } else {
                logger.error("Ya registró su experiencia en ese puesto y en dicha empresa");
                response.put(EstadoMensaje.ERROR.toString(), "Ya registró su experiencia en ese puesto y en dicha empresa");
                return ResponseEntity.status(409).body(response);
            }
        } else {
            response.put(EstadoMensaje.ERROR.toString(), "La persona informada no está registrada");
            return ResponseEntity.status(404).body(response);
        }
    }

    public ResponseEntity<JsonObject> eliminarExperiencia(Integer id) {
        JsonObject response = new JsonObject();
        Optional<Experiencia> experiencia = experienciaRepository.findById(id);
        if (experiencia.isPresent()) {
            Experiencia exp = experiencia.get();
            exp.getUser().removeExperiencia(exp);
            experienciaRepository.delete(experiencia.get());
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }

    private ResponseEntity<JsonObject> actualizarDatosExperiencia(Integer id, ExperienciaDTO e) {
        JsonObject response = new JsonObject();
        return experienciaRepository.findById(id).
                map(experiencia -> {
                    if (!experiencia.getEmpresa().equalsIgnoreCase(e.getEmpresa()))
                        experiencia.setEmpresa(ucFirst(e.getEmpresa()));
                    if (!experiencia.getDescripcion().equalsIgnoreCase(e.getDescripcion()))
                        experiencia.setDescripcion(ucFirst(e.getDescripcion()));
                    if (experiencia.getFin() != null && !experiencia.getFin().equals(e.getFin())) {
                        if (!experiencia.getActual())
                            experiencia.setFin(e.getFin());
                        else
                            experiencia.setFin(null);
                    }
                    if (!experiencia.getInicio().equals(e.getInicio()))
                        experiencia.setInicio(e.getInicio());
                    if (!experiencia.getPuesto().equalsIgnoreCase(e.getPuesto()))
                        experiencia.setPuesto(ucFirst(e.getPuesto()));
                    if (!experiencia.getActual().equals(e.getActual()))
                        experiencia.setActual(e.getActual());

                    response.put(EstadoMensaje.SUCCESS.toString(), experienciaRepository.save(experiencia));
                    return ResponseEntity.status(200).body(response);
                })
                .orElseGet(() -> {
                    response.put(EstadoMensaje.ERROR.toString(), "No se pueden actualizar los datos porque la experiencia no esta registrada");
                    return ResponseEntity.status(409).body(response);
                });
    }

    public ResponseEntity<JsonObject> actualizarExperiencia(Integer id, ExperienciaDTO e) {
        JsonObject response = new JsonObject();
        Experiencia experiencia = experienciaRepository.findByEmpresaAndPuestoAndIdPersona(e.getEmpresa(),e.getPuesto(),e.getIdPersona());
        if (experiencia.getPuesto().equalsIgnoreCase(e.getPuesto()) && experiencia.getEmpresa().equalsIgnoreCase(e.getEmpresa()) && experiencia.getId() != id) {
            response.put(EstadoMensaje.ERROR.toString(), "Ya hay una experiencia con esos datos, actualice la anterior.");
            return ResponseEntity.status(409).body(response);
        }
        return actualizarDatosExperiencia(id,e);
    }

}
