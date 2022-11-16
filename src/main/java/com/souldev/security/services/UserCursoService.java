package com.souldev.security.services;


import com.souldev.security.dto.UserCursoDTO;
import com.souldev.security.entities.Curso;
import com.souldev.security.entities.UserCurso;
import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.repositories.CursoRepository;
import com.souldev.security.repositories.UserCursoRepository;
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
public class UserCursoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UserCursoRepository userCursoRepository;





    Logger logger = Logger.getLogger(getClass().getName());


    public ResponseEntity<JsonObject> agregarCurso(UserCursoDTO p) {
        Optional<Curso> cursoOptional = cursoRepository.findById(p.getIdCurso());
        Optional<User> personaOptional = userRepository.findById(p.getIdPersona());
        JsonObject response = new JsonObject();
        if(cursoOptional.isPresent() && personaOptional.isPresent()) {
            if(userCursoRepository.countPersonaCursosByidPersonaAndidCurso(p.getIdPersona(),p.getIdCurso()) != 0){
                response.put(EstadoMensaje.ERROR.toString(), "La persona ya está registrada en dicho curso");
                return ResponseEntity.status(409).body(response);
            }
            logger.info(p.getDescripcion());
            Curso curso = cursoOptional.get();
            User persona = personaOptional.get();
            UserCurso persona_curso = new UserCurso();
            persona_curso.setUser(persona);
            persona_curso.setCurso(curso);
            persona_curso.setDescripcion(p.getDescripcion());
            persona_curso.setInicio(p.getInicio());
            persona_curso.setFinalizado(p.getFinalizado());
            ArrayList<String> errores = new ArrayList();
            if (p.getFinalizado()) {
                boolean error = false;
                if (p.getFin() == null) {
                    errores.add("Si finalizó el curso, debe indicar la fecha de finalizacion");
                    error = true;
                }else{
                    persona_curso.setFin(p.getFin());
                }
                if (p.getCertificado() == null || p.getCertificado().isEmpty() || p.getCertificado().isBlank()) {
                    errores.add("Si finalizó el curso, debe proporcionar la URL del certificado");
                    error = true;
                }else{
                    persona_curso.setCertificado(p.getCertificado());
                }
                if(p.getInicio().isAfter(p.getFin())){
                    errores.add("La fecha de fin no puede ir antes que la de inicio");
                    error = true;
                }else{
                    persona_curso.setFin(p.getFin());
                }

                if (error) {
                    response.put(EstadoMensaje.ERROR.toString(),errores);
                    return ResponseEntity.status(400).body(response);
                }
            }else{
                persona_curso.setFin(null);
                persona_curso.setCertificado(null);
            }
            //persona.addCurso(curso);
            response.put(EstadoMensaje.SUCCESS.toString(), userCursoRepository.save(persona_curso).getCurso());
            return ResponseEntity.status(200).body(response);
        }else{
            response.put(EstadoMensaje.ERROR.toString(), "El curso y/o persona indicada no está registrado");
            return ResponseEntity.status(404).body(response);
        }
    }


    public ResponseEntity<JsonObject> listarCursosPorPersona(Integer idPersona) {
        JsonObject response = new JsonObject();
        if(userRepository.findById(idPersona).isPresent()){
            Set<Curso> cursos = userCursoRepository.findCursosByPersonaId(idPersona);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), cursos));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }


    public ResponseEntity<JsonObject> listarPersonasPorCurso(Integer idCurso) {
        JsonObject response = new JsonObject();
        if(cursoRepository.findById(idCurso).isPresent()){
            Set<User> personas = userCursoRepository.findPersonasByCursoId(idCurso);
            return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), personas));
        }
        return ResponseEntity.status(200).body(response.put(EstadoMensaje.SUCCESS.toString(), "[]"));
    }


    public ResponseEntity<JsonObject> quitarCurso(Integer idPersona, Integer idCurso){
        JsonObject response = new JsonObject();
        Optional<UserCurso> entityOptional = userCursoRepository.findByidPersonaAndidCurso(idPersona,idCurso);
        if (entityOptional.isPresent()) {
            /*UserCurso entity = entityOptional.get();
            logger.info("Entidad a borrar: " + entity.getId());
            userCursoRepository.deleteById(entity.getId());
             */
            userCursoRepository.deleteByidPersonaAndidCurso(idPersona,idCurso);
        }
        response.put(EstadoMensaje.SUCCESS.toString(), "Eliminacion exitosa");
        return ResponseEntity.status(200).body(response);
    }




}
