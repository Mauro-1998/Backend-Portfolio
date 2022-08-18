package com.souldev.security.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
public class CursoDTO {

    @NotNull
    @Min(1)
    private Integer idCurso;

    @Size(min = 1,max = 100)
    private String nombre;

    public CursoDTO(){}

}