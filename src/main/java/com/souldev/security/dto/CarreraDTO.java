package com.souldev.security.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CarreraDTO {

    @NotNull
    @NotEmpty
    private String carrera;
    @NotNull
    @NotEmpty
    private String facultad;
}
