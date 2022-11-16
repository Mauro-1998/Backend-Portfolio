package com.souldev.security.dto;

import java.util.Set;

import com.souldev.security.entities.Experiencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResumeDTO {
    private Set <Experiencia> experiencias;
    private Set<UserCarreraDTO> carreras;


}
