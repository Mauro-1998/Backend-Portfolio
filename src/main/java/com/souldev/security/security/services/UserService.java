package com.souldev.security.security.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.souldev.security.dto.AboutMeDTO;
import com.souldev.security.dto.PersonaDTO;
import com.souldev.security.dto.ResumeDTO;
import com.souldev.security.dto.UserCarreraDTO;
import com.souldev.security.dto.UserDTO;
import com.souldev.security.entities.Carrera;
import com.souldev.security.entities.UserCarrera;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.mapper.UserMapper;
import com.souldev.security.repositories.ExperienciaRepository;
import com.souldev.security.repositories.ReferenciaRepository;
import com.souldev.security.repositories.UserCarreraRepository;
import com.souldev.security.security.entities.User;
import com.souldev.security.security.respositories.UserRepository;
import com.souldev.security.services.PersonaCarreraService;

import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReferenciaRepository referenciaRepository;

    @Autowired 
    private UserCarreraRepository userCarreraRepository;



    UserMapper userMapper = new UserMapper();

    Logger logger = Logger.getLogger(getClass().getName());

    public ResponseEntity<AboutMeDTO> listarPersonaAboutMe(String email){
        AboutMeDTO aboutMeDTO = new AboutMeDTO();
        Optional<User> user = userRepository.findByEmail(email);
        User u;
        if(user.isPresent()){
            u = user.get();
            aboutMeDTO.setUserDTO(new UserDTO(u.getNombre(),u.getApellido(),u.getNacimiento(),u.getDescripcion(),u.getDomicilio(),u.getEmail(),u.getTelefono(),u.getUrlFoto()));
            aboutMeDTO.setReferencias(referenciaRepository.findAllUserReferences(u.getId()));
            return ResponseEntity.status(200).body(aboutMeDTO);
        }else{
            logger.error("El email no es valido");
        }
        
        return null;
    }


    public ResponseEntity<ResumeDTO> listarPersonaResume(String email){
        ResumeDTO resumeDTO = new ResumeDTO();
        Optional<User> user = userRepository.findByEmail(email);
        User u;
        if(user.isPresent()){
            u = user.get();
            Set<Carrera> carreras = userCarreraRepository.findCarrerasByPersonaId(u.getId());
            Set<UserCarreraDTO> userCarreraSet = new HashSet<>();
            
            for(Carrera c: carreras){
                Optional<UserCarrera> ucOptional = userCarreraRepository.findByidPersonaAndidCarrera(u.getId(), c.getId());
                if(ucOptional.isPresent()){
                    UserCarrera u_c = ucOptional.get();
                    UserCarreraDTO uc = new UserCarreraDTO();
                    uc.setCarrera(c.getCarrera());
                    uc.setFacultad(c.getFacultad());
                    uc.setFin(u_c.getFin());
                    uc.setInicio(u_c.getInicio());
                    uc.setFinalizado(u_c.getFinalizado());
                    uc.setDescripcion(u_c.getDescripcion());
                    userCarreraSet.add(uc);
                }
            }
            resumeDTO.setCarreras(userCarreraSet);
            resumeDTO.setExperiencias(u.getExperiencias());
            return ResponseEntity.status(200).body(resumeDTO);
        }else{
            logger.error("El email no es valido");
        }
        
        return null;
    }


    public ResponseEntity<List> listarPersonas() {
        
        List<User> personas = userRepository.findAll();

        return ResponseEntity.status(200).body(personas);
    }

    public ResponseEntity<JsonObject> guardar(User p) {
        JsonObject response = new JsonObject();
        if (userRepository.countByEmailOrTelefono(p.getEmail(), p.getTelefono()) == 0) {
            if(p.getPassword().equals("")){
                response.put(EstadoMensaje.ERROR.toString(), "La contraseña no puede ser vacia");
                return ResponseEntity.status(400).body(response);
            }
            logger.info("ROLES SERVICE PERSONA: " + p.getRoles());

            User persona = userRepository.save(p);
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


    public ResponseEntity<JsonObject> actualizarPersona(Integer id, PersonaDTO p) {
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
                        if (!userRepository.findByEmail(p.getEmail()).isPresent()) {
                            persona.setEmail(p.getEmail());
                        } else {
                            response.put(EstadoMensaje.ERROR.toString(), "El correo ya está registrado");
                            return ResponseEntity.status(409).body(response);
                        }
                    }

                    if(!p.getPassword().equals(p.getPassword2())){
                        response.put(EstadoMensaje.ERROR.toString(), "Las contraseñas no coinciden");
                        return ResponseEntity.status(409).body(response);
                    }else{
                        persona.setPassword(passwordEncoder.encode(p.getPassword()));
                    }


                    if(!persona.getDomicilio().equals(p.getDomicilio()))
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

    public Optional<User> getByUserEmail(String email){
        return userRepository.findByEmail(email);
    }
    
}
