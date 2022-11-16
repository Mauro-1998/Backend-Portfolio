package com.souldev.security.mapper;


import com.souldev.security.dto.ProyectoDTO;
import com.souldev.security.entities.Proyecto;
import org.modelmapper.ModelMapper;

public class ProyectoMapper {

    ModelMapper mapper;


    public ProyectoMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
    }

    public Proyecto toEntity(ProyectoDTO p) {
        return mapper.map(p, Proyecto.class);
    }

    public ProyectoDTO toModel(Proyecto p) {
        return mapper.map(p, ProyectoDTO.class);
    }
}
