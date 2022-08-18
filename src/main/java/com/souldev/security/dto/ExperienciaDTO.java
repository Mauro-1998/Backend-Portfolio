package com.souldev.security.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class ExperienciaDTO {

    @NotNull
    @Size(min = 1, max = 45)
    private String empresa;
    @NotNull
    @Size(min = 1, max = 45)
    private String puesto;
    @NotNull
    @Size(min = 1, max = 500)
    private String descripcion;
    @NotNull
    private Date inicio;
    private Date fin;
    @NotNull
    private Boolean actual;
    @NotNull
    private Integer idPersona;


}