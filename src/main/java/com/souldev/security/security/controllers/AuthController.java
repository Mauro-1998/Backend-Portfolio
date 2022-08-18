package com.souldev.security.security.controllers;

import com.souldev.security.enums.EstadoMensaje;
import com.souldev.security.security.dtos.JwtDto;
import com.souldev.security.security.dtos.LoginUser;
import com.souldev.security.security.dtos.NewUser;
import com.souldev.security.security.entities.Role;
import com.souldev.security.security.entities.User;
import com.souldev.security.security.enums.RoleList;
import com.souldev.security.security.jwt.JwtProvider;
import com.souldev.security.security.services.RoleService;
import com.souldev.security.security.services.UserService;
import io.vertx.core.json.JsonObject;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtProvider jwtProvider;

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder, UserService userService, RoleService roleService, JwtProvider jwtProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JsonObject> login(@Valid @RequestBody LoginUser loginUser, BindingResult bidBindingResult) {
        logger.info("LOGIN AUTH");
        JsonObject response = new JsonObject();
        if (bidBindingResult.hasErrors()) {
            response.put(EstadoMensaje.ERROR.toString(), "Revise los campos e intente nuevamente");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
            logger.info("AuthenticationToken: " + authenticationToken);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            logger.info("Authentication: " + authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());
            String jwt = jwtProvider.generateToken(authentication);
            logger.info("Token: " + jwt);
            JwtDto jwtDto = new JwtDto(jwt);
            response.put("token",jwtDto.getToken());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("ERROR LOGIN: " + e.getMessage());
            response.put(EstadoMensaje.ERROR.toString(), "Revise sus credenciales");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JsonObject> resgister(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        logger.info("REGISTER AUTH");
        JsonObject response = new JsonObject();
        if (bindingResult.hasErrors()) {
            logger.error("ERROR Register");
            response.put(EstadoMensaje.ERROR.toString(), "Revise los campos e intente nuevamente");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = new User(newUser.getNombre(), newUser.getApellido(), newUser.getDescripcion(),newUser.getNacimiento(),newUser.getTelefono(), newUser.getEmail(), passwordEncoder.encode(newUser.getPassword()), newUser.getDomicilio(), newUser.getUrlFoto());

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleList.ROLE_USER).get());

        if (newUser.getRoles().contains("admin")) {
            roles.add(roleService.getByRoleName(RoleList.ROLE_ADMIN).get());
        }
        user.setRoles(roles);
        return userService.guardar(user);

    }

}
