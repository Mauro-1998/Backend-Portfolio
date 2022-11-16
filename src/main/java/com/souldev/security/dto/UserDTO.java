package com.souldev.security.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private String nombre;
    private String apellido;
    private LocalDate nacimiento;
    private String descripcion;
    private String domicilio;
    private String email;
    private String telefono;
    private String urlFoto;
}
