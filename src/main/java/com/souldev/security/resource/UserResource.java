package com.souldev.security.resource;


import com.souldev.security.dto.AboutMeDTO;
import com.souldev.security.dto.PersonaDTO;
import com.souldev.security.dto.ResumeDTO;
import com.souldev.security.dto.UserEmailDTO;
import com.souldev.security.resource.rest.UserClient;
import com.souldev.security.security.services.UserService;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import javax.validation.Valid;


@RestController
@RequestMapping("/persona")
@ControllerAdvice
@CrossOrigin("*")
public class UserResource extends ResponseEntityExceptionHandler implements UserClient {


    @Autowired
    UserService userService;


    @Override
    public ResponseEntity<List> listarPersonas() {
        return userService.listarPersonas();
    }

    @Override
    public ResponseEntity<AboutMeDTO> listarPersonaAboutMe(UserEmailDTO userEmailDTO) {
        return userService.listarPersonaAboutMe(userEmailDTO.getEmail());
    }

    @Override
    public ResponseEntity<ResumeDTO> listarPersonaResume(UserEmailDTO userEmailDTO) {
        return userService.listarPersonaResume(userEmailDTO.getEmail());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JsonObject> borrarPersona(Integer id) {
        return userService.eliminarPersona(id);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JsonObject> actualizarPersona(Integer id, @Valid PersonaDTO p) { return userService.actualizarPersona(id, p); }

    



}
