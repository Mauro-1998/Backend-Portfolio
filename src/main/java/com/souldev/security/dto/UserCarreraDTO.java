package com.souldev.security.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCarreraDTO{
    private String carrera;
    private String facultad;
    private LocalDate inicio;
    private LocalDate fin;
    private Boolean finalizado;
    private String descripcion;
}
