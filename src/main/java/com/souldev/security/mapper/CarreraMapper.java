package com.souldev.security.mapper;


import com.souldev.security.dto.CarreraDTO;
import com.souldev.security.entities.Carrera;
import org.modelmapper.ModelMapper;

public class CarreraMapper {
    static ModelMapper mapper;

    public CarreraMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
    }

    public Carrera toEntity(CarreraDTO c) {
        return mapper.map(c, Carrera.class);
    }

    public CarreraDTO toModel(Carrera c) {
        return mapper.map(c, CarreraDTO.class);
    }
}
