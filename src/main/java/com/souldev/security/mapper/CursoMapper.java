package com.souldev.security.mapper;



import com.souldev.security.dto.CursoDTO;
import com.souldev.security.entities.Curso;
import org.modelmapper.ModelMapper;

public class CursoMapper {
    static ModelMapper mapper;

    public CursoMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
    }

    public Curso toEntity(CursoDTO c) {
        return mapper.map(c, Curso.class);
    }

    public CursoDTO toModel(Curso c) {
        return mapper.map(c, CursoDTO.class);
    }
}
