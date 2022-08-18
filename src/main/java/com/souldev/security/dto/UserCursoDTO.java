package com.souldev.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserCursoDTO {

    @NotNull
    @Min(value = 1, message = "Debe indicar un id valido de persona")
    private Integer idPersona;

    @NotNull
    @Min(value = 1, message = "Debe indicar un id valido de curso")
    private Integer idCurso;

    private String nombreCurso;

    @NotNull
    @NotEmpty(message = "Debe agregar una descripcion de lo realizado en el curso")
    @Size(min = 1, max = 500)
    private String descripcion;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fin;

    @NotNull
    private Boolean finalizado;

    @Size(max = 300)
    private String certificado;

}
