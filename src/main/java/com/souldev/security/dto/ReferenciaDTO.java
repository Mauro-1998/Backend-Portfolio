package com.souldev.security.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class ReferenciaDTO {

    @NotNull
    @Size(min = 1, max = 45)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 45)
    private String puesto;
    @NotNull
    @Size(min = 1, max = 300)
    private String descripcion;
    @NotNull
    @Size(min = 1, max = 100)
    private String fotoURL;
    @NotNull
    @Min(1)
    private Integer idPersona;

}
