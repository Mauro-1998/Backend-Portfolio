package com.souldev.security.services;


import com.souldev.security.entities.UserCurso;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.mapper.UserMapper;
import com.souldev.security.repositories.CursoRepository;
import com.souldev.security.security.dtos.NewUser;
import com.souldev.security.security.entities.User;
import com.souldev.security.security.respositories.UserRepository;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PersonaService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UserMapper personaMapper = new UserMapper();

    Logger logger = Logger.getLogger(getClass().getName());


    public ResponseEntity<JsonObject> listarPersonas() {
        JsonObject response = new JsonObject();
        List<User> personas = userRepository.findAll();

        response.put(EstadoMensaje.SUCCESS.toString(), personas);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<JsonObject> guardar(NewUser p) {
        JsonObject response = new JsonObject();
        if (userRepository.countByEmailOrTelefono(p.getEmail(), p.getTelefono()) == 0) {
            if(p.getPassword().equals("")){
                response.put(EstadoMensaje.ERROR.toString(), "La contraseña no puede ser vacia");
                return ResponseEntity.status(400).body(response);
            }
            if(!p.getPassword().equals(p.getPassword2())){
                logger.error("Las contraseñas no coinciden");
                response.put(EstadoMensaje.ERROR.toString(), "Las contraseñas no coinciden");
                return ResponseEntity.status(400).body(response);
            }
            // Hasheamos la contraseña
            p.setPassword(passwordEncoder.encode(p.getPassword()));
            logger.info("ROLES SERVICE PERSONA: " + personaMapper.toEntity(p).getRoles());

            User persona = userRepository.save(personaMapper.toEntity(p));
            response.put(EstadoMensaje.SUCCESS.toString(), persona);
            return ResponseEntity.status(200).body(response);
        } else {
            response.put(EstadoMensaje.ERROR.toString(), "Ya hay una persona registrada con ese telefono o correo");
            return ResponseEntity.status(409).body(response);
        }
    }

    public ResponseEntity<JsonObject> eliminarPersona(Integer id) {
        JsonObject response = new JsonObject();
        Optional<User> entityOptional = userRepository.findById(id);
        if (entityOptional.isPresent()) {
            User entity = entityOptional.get();
            userRepository.delete(entity);
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<JsonObject> actualizarPersona(Integer id, NewUser p) {
        JsonObject response = new JsonObject();
        return userRepository.findById(id).
                map(persona -> {
                    if (!persona.getNombre().equals(p.getNombre()))
                        persona.setNombre(p.getNombre());
                    if (!persona.getApellido().equals(p.getApellido()))
                        persona.setApellido(p.getApellido());
                    if (!persona.getDescripcion().equals(p.getDescripcion()))
                        persona.setDescripcion(p.getDescripcion());
                    if (!persona.getNacimiento().equals(p.getNacimiento()))
                        persona.setNacimiento(p.getNacimiento());
                    if (!persona.getTelefono().equals(p.getTelefono())) {
                        if (userRepository.findByTelefono(p.getTelefono()) == null) {
                            persona.setTelefono(p.getTelefono());
                        } else {
                            response.put(EstadoMensaje.ERROR.toString(), "El telefono ya está registrado");
                            return ResponseEntity.status(409).body(response);
                        }
                    }
                    if (!persona.getEmail().equals(p.getEmail())) {
                        if (userRepository.findByEmail(p.getEmail()) == null) {
                            persona.setEmail(p.getEmail());
                        } else {
                            response.put(EstadoMensaje.ERROR.toString(), "El correo ya está registrado");
                            return ResponseEntity.status(409).body(response);
                        }
                    }
                    if (!persona.getDomicilio().equals(p.getDomicilio()))
                        persona.setDomicilio(p.getDomicilio());
                    if (!persona.getUrlFoto().equals(p.getUrlFoto()))
                        persona.setUrlFoto(p.getUrlFoto());

                    response.put(EstadoMensaje.SUCCESS.toString(), userRepository.save(persona));
                    return ResponseEntity.status(200).body(response);

                })
                .orElseGet(() -> {
                    response.put(EstadoMensaje.ERROR.toString(), "No se pueden actualizar los datos porque la persona no esta registrada");
                    return ResponseEntity.status(409).body(response);
                });
    }




}
