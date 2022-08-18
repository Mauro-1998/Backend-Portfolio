package com.souldev.security.mapper;


import com.souldev.security.dto.ReferenciaDTO;
import com.souldev.security.entities.Referencia;
import org.modelmapper.ModelMapper;

public class ReferenciaMapper {
    ModelMapper mapper;

    public ReferenciaMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
    }

    public Referencia toEntity(ReferenciaDTO r) {
        return mapper.map(r, Referencia.class);
    }

    public ReferenciaDTO toModel(Referencia r) {
        return mapper.map(r, ReferenciaDTO.class);
    }
}
