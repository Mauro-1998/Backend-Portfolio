package com.souldev.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
public class PersonaDTO {

    @Autowired
    PasswordEncoder passwordEncoder;

    @NotNull
    @NotEmpty(message = "El campo nombre no puede ser vacio")
    @Size(min = 1, max = 45)
    private String nombre;

    @NotNull
    @NotEmpty(message = "El campo apellido no puede ser vacio")
    @Size(min = 1, max = 45)
    private String apellido;

    @NotNull
    @NotEmpty(message = "Debe colocar una descripcion suya")
    @Size(min = 1, max = 600)
    private String descripcion;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nacimiento;

    @NotNull
    @NotEmpty(message = "El campo telefono no puede ser vacio")
    @Size(max = 20)
    private String telefono;

    @NotNull
    @NotEmpty(message = "El correo no puede ser vacio")
    @Email(message = "No sigue un formato de correo valido")
    @Size(max = 60)
    private String email;


    @NotNull
    @NotEmpty(message = "La contraseña no puede ser vacia")
    @Size(max = 200)
    @ApiModelProperty(notes = "Repeticion de contraseña", example = "password", required = true, hidden = true)
    @ApiParam(format="password")
    private String password;

    @NotNull
    @NotEmpty(message = "La contraseña no puede ser vacia")
    @Size(max = 200)
    @ApiModelProperty(notes = "Repeticion de contraseña", example = "password", required = true, hidden = true)
    @ApiParam(format="password")
    private String password2;


    @NotNull
    @NotEmpty(message = "El campo domicilio no puede ser vacio")
    @Size(max = 45)
    private String domicilio;

    @NotNull
    @NotEmpty(message = "Debe colocar la URL de una foto suya")
    @Size(max = 300)
    private String urlFoto;



}
