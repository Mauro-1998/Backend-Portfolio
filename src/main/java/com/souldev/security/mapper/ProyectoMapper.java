package com.souldev.security.mapper;


import com.souldev.security.dto.ProyectoDTO;
import com.souldev.security.entities.Proyecto;
import com.souldev.security.repositories.ProyectoRepository;
import com.souldev.security.security.respositories.UserRepository;
import com.souldev.security.security.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
