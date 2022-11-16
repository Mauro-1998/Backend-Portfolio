package com.souldev.security.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.souldev.security.entities.Referencia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AboutMeDTO {
    private UserDTO userDTO;
    private Set referencias;
}
