package com.souldev.security.services;


import com.souldev.security.dto.PersonaCarreraDTO;
import com.souldev.security.entities.Carrera;
import com.souldev.security.entities.UserCarrera;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.repositories.CarreraRepository;
import com.souldev.security.repositories.UserCarreraRepository;
import com.souldev.security.security.entities.User;
import com.souldev.security.security.respositories.UserRepository;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonaCarreraService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCarreraRepository userCarreraRepository;

    @Autowired
    CarreraRepository carreraRepository;



    Logger logger = Logger.getLogger(getClass().getName());


    public ResponseEntity<JsonObject> agregarCarrera(PersonaCarreraDTO p) {
        Optional<Carrera> carreraOptional = carreraRepository.findById(p.getIdCarrera());
        Optional<User> personaOptional = userRepository.findById(p.getIdPersona());
        JsonObject response = new JsonObject();
        if(carreraOptional.isPresent() && personaOptional.isPresent()) {
            //logger.info("PERSONA_CARRERA: " + personaCarreraRepository.findByidPersonaAndidCarrera(p.getIdPersona(),p.getIdCarrera()));
            Optional<UserCarrera> p_c = userCarreraRepository.findByidPersonaAndidCarrera(p.getIdPersona(),p.getIdCarrera());
            if(p_c.isPresent()){
                logger.info(p_c.get().toString());
                response.put(EstadoMensaje.ERROR.toString(), "La persona ya está registrada en dicha carrera");
                return ResponseEntity.status(409).body(response);
            }
            logger.info(p.getDescripcion());
            Carrera carrera = carreraOptional.get();
            User persona = personaOptional.get();
            UserCarrera persona_carrera = new UserCarrera();
            persona_carrera.setUser(persona);
            persona_carrera.setCarrera(carrera);
            persona_carrera.setDescripcion(p.getDescripcion());
            persona_carrera.setInicio(p.getInicio());
            persona_carrera.setFinalizado(p.getFinalizado());
            ArrayList<String> errores = new ArrayList();
            if (p.getFinalizado()) {
                boolean error = false;
                if (p.getFin() == null) {
                    errores.add("Si finalizó la carrera, debe indicar la fecha de finalizacion");
                    error = true;
                }
                if(p.getInicio().isAfter(p.getFin())){
                    errores.add("La fecha de fin no puede ir antes que la de inicio");
                    error = true;
                }
                if (error) {
                    response.put(EstadoMensaje.ERROR.toString(),errores);
                    return ResponseEntity.status(400).body(response);
                }else{
                    persona_carrera.setFin(p.getFin());
                }
            }else{
                persona_carrera.setFin(null);

            }
            response.put(EstadoMensaje.SUCCESS.toString(), userCarreraRepository.save(persona_carrera).getCarrera());
            return ResponseEntity.status(200).body(response);
        }else{
            response.put(EstadoMensaje.ERROR.toString(), "La carrera y/o persona indicada no está registrado");
            return ResponseEntity.status(404).body(response);
        }
    }


    public ResponseEntity<JsonObject> listarCarrerasPorPersona(Integer idPersona) {
        JsonObject response = new JsonObject();
        if(userRepository.findById(idPersona).isPresent()){
            Set<Carrera> carreras = userCarreraRepository.findCarrerasByPersonaId(idPersona);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), carreras));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }


    public ResponseEntity<JsonObject> listarPersonasPorCarrera(Integer idCarrera) {
        JsonObject response = new JsonObject();
        if(carreraRepository.findById(idCarrera).isPresent()){
            Set<User> personas = userCarreraRepository.findPersonasByCarreraId(idCarrera);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), personas));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }


    public ResponseEntity<JsonObject> quitarCarrera(Integer idPersona, Integer idCarrera){
        JsonObject response = new JsonObject();
        Optional<UserCarrera> entityOptional = userCarreraRepository.findByidPersonaAndidCarrera(idPersona,idCarrera);
        if (entityOptional.isPresent()) {
            logger.info("Entro bien");
            UserCarrera entity = entityOptional.get();
            logger.info(entity.toString());
            userCarreraRepository.delete(entity);
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }



}
