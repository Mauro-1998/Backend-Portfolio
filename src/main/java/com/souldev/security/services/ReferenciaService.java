package com.souldev.security.services;


import com.souldev.security.dto.ReferenciaDTO;
import com.souldev.security.entities.Referencia;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.mapper.ReferenciaMapper;
import com.souldev.security.repositories.ReferenciaRepository;
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
import static com.souldev.security.common.Functions.ucNombres;

@Service
public class ReferenciaService {

    @Autowired
    ReferenciaRepository referenciaRepository;

    @Autowired
    UserRepository personaRepository;

    Logger logger = Logger.getLogger(getClass().getName());

    ReferenciaMapper mapper = new ReferenciaMapper();


    public ResponseEntity<JsonObject> listarReferencias(Integer id) {
        JsonObject response = new JsonObject();
        if (personaRepository.findById(id).isPresent()) {
            Set<Referencia> referencias = referenciaRepository.findAllUserReferences(id);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), referencias));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }


    public ResponseEntity<JsonObject> guardar(ReferenciaDTO r) {
        JsonObject response = new JsonObject();
        Optional<User> personaOptional = personaRepository.findById(r.getIdPersona());
        if (personaOptional.isPresent()) {
            User per = personaOptional.get();
            Referencia ref = new Referencia();
            ref.setUser(per);
            ref.setNombre(ucNombres(r.getNombre()));
            ref.setPuesto(ucNombres(r.getPuesto()));
            ref.setDescripcion(ucFirst(r.getDescripcion()));
            ref.setUrlFoto(r.getFotoURL());
            Referencia referencia = referenciaRepository.save(ref);
            per.addReferencia(referencia);
            logger.info("Se insertó el dato correctamente");
            response.put(EstadoMensaje.SUCCESS.toString(), referencia);
            return ResponseEntity.status(200).body(response);

        } else {
            logger.error("La persona informada no está registrada");
            response.put(EstadoMensaje.ERROR.toString(), "La persona informada no está registrada");
            return ResponseEntity.status(404).body(response);
        }
    }

    public ResponseEntity<JsonObject> eliminarReferencia(Integer id) {
        JsonObject response = new JsonObject();
        Optional<Referencia> referencia = referenciaRepository.findById(id);
        logger.info("En el service");
        if (referencia.isPresent()) {
            logger.info("Referencia presente");
            referenciaRepository.delete(referencia.get());
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<JsonObject> actualizarReferencia(Integer id, ReferenciaDTO r) {
        JsonObject response = new JsonObject();
        return referenciaRepository.findById(id).
                map(referencia -> {
                    if (!referencia.getNombre().equalsIgnoreCase(r.getNombre()))
                        referencia.setNombre(ucNombres(r.getNombre().toLowerCase()));
                    if (!referencia.getPuesto().equalsIgnoreCase(r.getPuesto()))
                        referencia.setPuesto(ucNombres(r.getPuesto().toLowerCase()));
                    if (!referencia.getDescripcion().equalsIgnoreCase(r.getDescripcion()))
                        referencia.setDescripcion(ucFirst(r.getDescripcion().toLowerCase()));
                    if (!referencia.getUrlFoto().equals(r.getFotoURL()))
                        referencia.setUrlFoto(r.getFotoURL());

                    response.put(EstadoMensaje.SUCCESS.toString(), referenciaRepository.save(referencia));
                    return ResponseEntity.status(200).body(response);
                })
                .orElseGet(() -> {
                    response.put(EstadoMensaje.ERROR.toString(), "No se pueden actualizar los datos porque la experiencia no esta registrada");
                    return ResponseEntity.status(409).body(response);
                });
    }


}
