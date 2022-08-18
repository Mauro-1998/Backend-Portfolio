package com.souldev.security.mapper;


import com.souldev.security.dto.UserCursoDTO;
import com.souldev.security.entities.UserCurso;
import com.souldev.security.security.entities.User;
import io.vertx.core.json.JsonObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class PersonaCursoMapper {

    ModelMapper mapper;

    public PersonaCursoMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }

    public UserCurso toEntity(UserCursoDTO p) {
        return mapper.map(p, UserCurso.class);
    }

    public UserCursoDTO toModel(UserCurso p) {
        return mapper.map(p, UserCursoDTO.class);
    }

    public JsonObject toJson(User p) {
        return mapper.map(p, JsonObject.class);
    }
}
