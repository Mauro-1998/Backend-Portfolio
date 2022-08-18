package com.souldev.security.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class ProyectoDTO {

    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 300)
    private String descripcion;
    @NotNull
    @Size(min = 1, max = 300)
    @URL
    private String fotoURL;
    @NotNull
    @Min(1)
    private Integer idPersona;

}
