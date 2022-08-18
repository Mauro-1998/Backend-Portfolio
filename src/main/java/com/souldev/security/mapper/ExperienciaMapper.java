package com.souldev.security.mapper;

import com.souldev.security.dto.ExperienciaDTO;
import com.souldev.security.entities.Experiencia;
import com.souldev.security.security.entities.User;
import com.souldev.security.security.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ExperienciaMapper {

    static ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    public ExperienciaMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
        }
    }

    public Experiencia toEntity(ExperienciaDTO e) {
        Optional<User> personaOptional = userRepository.findById(e.getIdPersona());
        User p = personaOptional.get();
        Experiencia exp = new Experiencia();
        exp.setActual(e.getActual());
        exp.setDescripcion(e.getDescripcion());
        exp.setEmpresa(e.getEmpresa());
        exp.setFin(e.getFin());
        exp.setInicio(e.getInicio());
        exp.setPuesto(e.getPuesto());
        exp.setUser(p);
        return exp;
    }

    public ExperienciaDTO toModel(Experiencia e) {
        return mapper.map(e, ExperienciaDTO.class);
    }
}